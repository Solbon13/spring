package com.example.demo.model.tasks;

import com.example.demo.model.Base;
import com.example.demo.model.organization.Person;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class TaskFile extends Base {
    private Long task_id;
    @Column(name = "filename_client")
    private String filenameClient;
    @Column(name = "filename_server")
    private String filenameServer;

    public TaskFile(String filenameClient, String filenameServer, Long task_id) {
        super();
        this.filenameClient = filenameClient;
        this.filenameServer = filenameServer;
        this.task_id = task_id;
    }

    public TaskFile() {

    }
}
