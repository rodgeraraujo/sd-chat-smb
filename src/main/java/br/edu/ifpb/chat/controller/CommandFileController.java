package br.edu.ifpb.chat.controller;

import br.edu.ifpb.chat.abstration.CommandFileService;
import br.edu.ifpb.chat.model.Message;
import br.edu.ifpb.chat.service.CommandFileServiceImpl;
import jcifs.smb.SmbFile;

import java.util.*;

public class CommandFileController {

    private CommandFileService commandFileService;

    public CommandFileController (CommandFileServiceImpl commandFileServiceImpl) {
        this.commandFileService = commandFileServiceImpl;
    }

    public ArrayList<Message> readFile(SmbFile file) {
        return commandFileService.readFile(file);
    }

    public void writeFile(SmbFile file, Message message){
        commandFileService.writeFile(file, message);
    }

}
