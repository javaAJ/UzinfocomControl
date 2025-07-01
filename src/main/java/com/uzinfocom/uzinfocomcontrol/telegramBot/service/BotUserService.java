package com.uzinfocom.uzinfocomcontrol.telegramBot.service;

import com.uzinfocom.uzinfocomcontrol.telegramBot.buttons.InlineButtonsService;
import com.uzinfocom.uzinfocomcontrol.telegramBot.buttons.KeyboardButtonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
public class BotUserService {
    @Autowired
    private InlineButtonsService inlineButtonsService;
    @Autowired
    private KeyboardButtonsService keyboardButtonsService;

    public SendMessage checkFullName(Long chatId, String fullName) {
        InlineKeyboardMarkup buttonMarkup = inlineButtonsService.check("fullName");
        String text = "Hammasi to`g`ri yozilganmi?\n\nMisol:\nAkbarov Jahongir Abror O`gli\n\nFamiliya Ism O`tvestva:\n" + fullName;

        return createSendMessage(chatId, text, buttonMarkup);
    }

    public SendMessage checkContact(Long chatId, String contact) {
        InlineKeyboardMarkup buttonMarkup = inlineButtonsService.check("contact");
        String text = "Hammasi to`g`ri yozilganmi?\n\nMisol:\n+998910110958\n\nContact:\n" + contact;

        return createSendMessage(chatId, text, buttonMarkup);
    }

    public SendMessage checkDateOfBirthday(Long chatId, String dateOfBirthday) {
        InlineKeyboardMarkup buttonMarkup = inlineButtonsService.check("dateOfBirthday");
        String text = "Hammasi to`g`ri yozilganmi?\n\nMisol:\nkun/oy/yil\n27/07/2006\n\nDate of Birthday:\n" + dateOfBirthday;

        return createSendMessage(chatId, text, buttonMarkup);
    }

    public SendMessage senFullName(Long chatId) {
        String text = "Familiya Ism Otchestangizni kiriting:\n\nMisol:\nAkbarov Jahongir Abror O`g`li";

        return createSendMessage(chatId, text, null);
    }

    public SendMessage sendContact(Long chatId) {
        String text = "O`zingizni kontaktingizni kiriting:\n\nMisol: +9989101098\n\nYoki kontact ulashishingiz mumkin.";
        ReplyKeyboardMarkup buttonMarkup = keyboardButtonsService.sendContact();
        return createSendMessage(chatId, text, buttonMarkup);
    }

    private SendMessage createSendMessage(Long chatId, String text, ReplyKeyboard button) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        if (button != null) {
            sendMessage.setReplyMarkup(button);
        }
        return sendMessage;
    }

    public SendMessage sendDateOfBirthday(Long chatId) {
        String text = "O`zingizni tug`ulgan kuningizni kiriting:\n\nMisol:\nkun/oy/yil\n27/7/2006";
        return createSendMessage(chatId, text, null);
    }

    public SendMessage sendFinishRegister(Long chatId) {
        String text = "Siz registratsiyadan o`ttingiz.\nNima yangiliklar bo`lsa shu botga jo`natiladi.";
        return createSendMessage(chatId, text, null);
    }


    public SendMessage sendErrorDateOfBirthday(Long chatId) {
        String text = "Siz tug`ulgan kuningizni xato kiritgansiz\nMisolga qarab tug`ulgan kuningizni kiriting:\n\nMisol:\nkun/oy/yil\n27/7/2006";
        return createSendMessage(chatId, text, null);
    }
}
