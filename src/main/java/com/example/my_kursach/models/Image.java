package com.example.my_kursach.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idimage",nullable = false)
    private Long idimage;
    @Lob
    private String bytes;
    public String decoder(String encoded)
    {
        Base64.Decoder decoder = Base64.getDecoder();
        String decoded = new String(decoder.decode(encoded));
        return decoded;
    }
    @Basic
    @Column(name = "isPreviewImage")
    private Boolean isPreviewImage;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "originalFileName")
    private String originalFileName;
    @Basic
    @Column(name = "size")
    private Long size;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Product product;




}
