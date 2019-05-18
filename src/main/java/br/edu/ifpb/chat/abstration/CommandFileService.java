package br.edu.ifpb.chat.abstration;

import br.edu.ifpb.chat.model.Message;
import jcifs.smb.SmbFile;

import java.util.*;

public interface CommandFileService {

    //lê no arquivo
    ArrayList<Message> readFile(SmbFile file);

    //escreve no arquivo
    void writeFile(SmbFile file, Message message);

}
