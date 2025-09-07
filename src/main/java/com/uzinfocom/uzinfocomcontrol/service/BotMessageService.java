package com.uzinfocom.uzinfocomcontrol.service;

import com.uzinfocom.uzinfocomcontrol.model.BotMessage;
import com.uzinfocom.uzinfocomcontrol.model.DTO.MessageDTO;
import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.repository.BotMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BotMessageService {

    @Autowired
    private BotMessageRepository botMessageRepository;

    public MessageDTO create(MessageDTO botMessageDTO, Department department) {
        BotMessage botMessage = new BotMessage();
        botMessage.setMessage(botMessageDTO.getMessage());
        botMessage.setTitle(botMessageDTO.getTitle());
        botMessage.setDepartment(department);

        return mapping(botMessageRepository.save(botMessage));
    }

    public BotMessage getById(Long id) {
        return botMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BotMessage с id=" + id + " не найден"));
    }

    public List<MessageDTO> getAll(Long departmentId) {
        return botMessageRepository.findAllByDepartment_Id(departmentId).stream().map(this::mapping).collect(Collectors.toList());
    }

    public MessageDTO update(Long id, MessageDTO updated) {
        BotMessage existing = botMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BotMessage с id=" + id + " не найден"));

        existing.setMessage(updated.getMessage());
        existing.setTitle(updated.getTitle());

        return mapping(botMessageRepository.save(existing));
    }

    public void delete(Long id) {
        if (!botMessageRepository.existsById(id)) {
            throw new RuntimeException("BotMessage с id=" + id + " не найден");
        }
        botMessageRepository.deleteById(id);
    }

    public MessageDTO mapping(BotMessage botMessage) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(botMessage.getId());
        messageDTO.setMessage(botMessage.getMessage());
        messageDTO.setTitle(botMessage.getTitle());
        return messageDTO;
    }

}
