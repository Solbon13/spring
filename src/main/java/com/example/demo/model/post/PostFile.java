package com.example.demo.model.post;

import com.example.demo.model.Base;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class PostFile extends Base {
    private Long post_id;
    @Column(name = "filename_client")
    private String filenameClient;
    @Column(name = "filename_server")
    private String filenameServer;

    public PostFile(String filenameClient, String filenameServer, Long post_id) {
        super();
        this.filenameClient = filenameClient;
        this.filenameServer = filenameServer;
        this.post_id = post_id;
    }

    public PostFile() {

    }
}
