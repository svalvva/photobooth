// File: src/main/java/com/photobooth/photoboothapp/service/PhotoStripService.java
package com.photobooth.photoboothapp.service;

import com.photobooth.photoboothapp.model.Customization;
import com.photobooth.photoboothapp.model.PhotoSession;
import com.photobooth.photoboothapp.model.PhotoStrip;
import com.photobooth.photoboothapp.model.Sticker;
import com.photobooth.photoboothapp.repository.PhotoStripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream; // <-- Import ini penting
import java.time.LocalDateTime;
import java.util.List;
// import java.util.Optional;

@Service
public class PhotoStripService {

    @Autowired
    private PhotoStripRepository photoStripRepository;

    @Autowired
    private PhotoSessionService photoSessionService;

    public PhotoStrip processAndSaveStrip(String sessionId, Customization customizationDetails) {
        PhotoSession session = photoSessionService.findSessionById(sessionId)
                .orElseThrow(() -> new RuntimeException("Sesi tidak ditemukan dengan ID: " + sessionId));

        try {
            int stripWidth = 480;
            int photoWidth = 440;
            int photoHeight = 330; 
            int margin = 20;
            int stripHeight = (photoHeight * session.getPhotoPaths().size()) + (margin * (session.getPhotoPaths().size() + 1));
            
            BufferedImage finalImage = new BufferedImage(stripWidth, stripHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = finalImage.createGraphics();
            
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            graphics.setColor(Color.decode(customizationDetails.getFrameColor()));
            graphics.fillRect(0, 0, stripWidth, stripHeight);

            int currentY = margin;
            for (String imagePath : session.getPhotoPaths()) {
                File imageFile = new File("uploads/" + new File(imagePath).getName());
                BufferedImage originalImage = ImageIO.read(imageFile);
                int currentX = (stripWidth - photoWidth) / 2;
                graphics.drawImage(originalImage, currentX, currentY, photoWidth, photoHeight, null);
                currentY += photoHeight + margin;
            }

            // --- INI PERBAIKAN UTAMANYA, SAYANG ---
            if (customizationDetails.getStickers() != null) {
                for (Sticker sticker : customizationDetails.getStickers()) {
                    // Cara baru yang lebih ampuh untuk membaca file dari dalam aplikasi
                    String stickerResourcePath = "/static/images/" + sticker.getStickerId() + ".png";
                    try (InputStream stickerStream = getClass().getResourceAsStream(stickerResourcePath)) {
                        if (stickerStream == null) {
                            System.err.println("Peringatan: File stiker tidak ditemukan di: " + stickerResourcePath);
                            continue; // Abaikan stiker ini jika filenya tidak ada, dan lanjutkan
                        }
                        BufferedImage stickerImage = ImageIO.read(stickerStream);
                        int stickerSize = 60;
                        // Gambar stiker di posisi yang sudah dicatat dari frontend
                        graphics.drawImage(stickerImage, sticker.getPositionX() - (stickerSize / 2), sticker.getPositionY() - (stickerSize / 2), stickerSize, stickerSize, null);
                    } catch (IOException e) {
                        System.err.println("Gagal membaca file stiker: " + sticker.getStickerId());
                    }
                }
            }
            // --- BATAS PERBAIKAN ---

            graphics.dispose();

            String outputFilename = "strip_" + System.currentTimeMillis() + ".png";
            File outputFile = new File("uploads/" + outputFilename);
            ImageIO.write(finalImage, "PNG", outputFile);

            PhotoStrip finalStrip = new PhotoStrip();
            finalStrip.setCreatorName("Syalwa's Masterpiece");
            finalStrip.setSourceImages(session.getPhotoPaths());
            finalStrip.setFinalStripUrl("/uploads/" + outputFilename);
            finalStrip.setCustomization(customizationDetails);
            finalStrip.setCreatedAt(LocalDateTime.now());

            return photoStripRepository.save(finalStrip);

        } catch (IOException e) {
            throw new RuntimeException("Gagal membuat gambar photo strip!", e);
        }
    }

    public List<PhotoStrip> getAllPhotoStrips() {
        return photoStripRepository.findAll();
    }
}