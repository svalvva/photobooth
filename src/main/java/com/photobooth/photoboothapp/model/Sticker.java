package com.photobooth.photoboothapp.model;
import lombok.Data;

@Data
public class Sticker {
    private String stickerId;
    private int positionX;
    private int positionY;
    private double size;
    private double rotation;
}