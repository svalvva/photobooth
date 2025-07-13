package com.photobooth.photoboothapp.model;
import java.util.List;
import lombok.Data;

@Data // <-- PASTIKAN INI ADA
public class Customization {
    private String frameColor;
    private List<Sticker> stickers;
}