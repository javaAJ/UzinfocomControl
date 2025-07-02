package com.uzinfocom.uzinfocomcontrol.telegramBot;

import com.uzinfocom.uzinfocomcontrol.model.RegisterLink;
import com.uzinfocom.uzinfocomcontrol.model.enums.Position;
import com.uzinfocom.uzinfocomcontrol.service.*;
import com.uzinfocom.uzinfocomcontrol.telegramBot.service.BotDepartmentService;
import com.uzinfocom.uzinfocomcontrol.telegramBot.service.BotUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MyBot extends TelegramLongPollingBot {

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private BirthdayService birthdayService;
    @Autowired
    private RegisterLinkService registerLinkService;
    @Autowired
    private BotUserService botUserService;
    @Autowired
    private BotDepartmentService botDepartmentService;


    public MyBot() {
        super("7577365277:AAEGCz9GiewtcI36aHOYj0M1kXIvU8TA8rk");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            Integer messageId = message.getMessageId();
            User telegramUser = message.getFrom();
            com.uzinfocom.uzinfocomcontrol.model.User user = userService.getById(chatId);

            if (message.hasText()) {
                String text = message.getText();
                if (text.startsWith("/start")) {
                    String[] commandParts = text.split("\\s+", 2);

                    if (commandParts.length > 1) {
                        String registerId = commandParts[1];


                        if (user != null) {
                            exec(new SendMessage(chatId + "", "Siz uji o`tdeldasiz"));
                            return;
                        }

                        user = userService.register(chatId, telegramUser.getUserName());
                        userService.setPosition(user, Position.REGISTER_FULL_NAME);
                        exec(new SendMessage(chatId + "", "Registratsiyani ohirigacham yetqazish uchun siznin ma`lumotlaringiz zarur.\n\nFamiliya Ism Otchestangizni kiriting\n\nMisol:\nAkbarov Jahongir Abror O`g`li"));

                        RegisterLink registerLink = registerLinkService.getByUniqId(Long.parseLong(registerId));
//                        departmentService.addUser(registerLink.getDepartment().getId(),user);
                        System.out.println(registerLink.getDepartment().getId());
                        userService.setDepartment(user, registerLink.getDepartment());

                    } else {
                        if (userService.getById(chatId) == null) {
                            exec(new SendMessage(chatId + "", "Siz link orqali ro`yhattan o`tishingiz kerak!!!\n\n\nO`zingizni o`tdelingizdagi kontrolyer bilan uchrashing"));
                        }
                    }
                } else {
                    if (user.getTelegramPosition().name().equals(Position.REGISTER_FULL_NAME.name())) {
                        userService.setFullName(user, text);
                        userService.setPosition(user, Position.CHECK);
                        exec(botUserService.checkFullName(chatId, text));
                    } else if (user.getTelegramPosition().name().equals(Position.REGISTER_CONTACT.name())) {
                        userService.setPhoneNumber(user, text);
                        userService.setPosition(user, Position.CHECK);
                        exec(botUserService.checkContact(chatId, text));
                    } else if (user.getTelegramPosition().name().equals(Position.REGISTER_DATE_OF_BIRTHDAY.name())) {
                        boolean isChanged = userService.setDateOfBirthday(user, text);
                        if (!isChanged) {
                            exec(botUserService.sendErrorDateOfBirthday(chatId));
                            return;
                        }
                        userService.setPosition(user, Position.CHECK);
                        exec(botUserService.checkDateOfBirthday(chatId, text));
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Long chatId = callbackQuery.getMessage().getChatId();
            Integer messageId = 0;
            if (callbackQuery.getMessage() instanceof Message message) {
                messageId = message.getMessageId();
            }

            com.uzinfocom.uzinfocomcontrol.model.User user = userService.getById(chatId);
            String data = callbackQuery.getData();

            if (data.startsWith("yes/")) {
                String command = data.split("/")[1];

                if (command.equals("fullName")) {
                    userService.setPosition(user, Position.REGISTER_CONTACT);
                    exec(botUserService.sendContact(chatId));
                } else if (command.equals("contact")) {
                    userService.setPosition(user, Position.REGISTER_DATE_OF_BIRTHDAY);
                    exec(botUserService.sendDateOfBirthday(chatId));
                } else if (command.equals("dateOfBirthday")) {
                    userService.setPosition(user, Position.WAIT);
                    exec(botUserService.sendFinishRegister(chatId));
                    birthdayService.save(user, user.getDepartment());
                }
                exec(new DeleteMessage(chatId + "", messageId));
            } else if (data.startsWith("not/")) {
                String command = data.split("/")[1];

                if (command.equals("fullName")) {
                    userService.setPosition(user, Position.REGISTER_FULL_NAME);
                    exec(botUserService.senFullName(chatId));
                } else if (command.equals("contact")) {
                    userService.setPosition(user, Position.REGISTER_CONTACT);
                    exec(botUserService.sendContact(chatId));
                } else if (command.equals("dateOfBirthday")) {
                    userService.setPosition(user, Position.REGISTER_DATE_OF_BIRTHDAY);
                    exec(botUserService.sendDateOfBirthday(chatId));
                }
                exec(new DeleteMessage(chatId + "", messageId));
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "@uzinfocom_control_bot";
    }

    public void exec(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void exec(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void exec(SendDocument sendDocument) {
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void exec(DeleteMessage deleteMessage) {
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void exec(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void exec(EditMessageCaption editMessageCaption) {
        try {
            execute(editMessageCaption);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendWishes(com.uzinfocom.uzinfocomcontrol.model.User user) {
        String fullName = user.getLastName() + " " + user.getFirstName() + " " + user.getPatronymic();
        String text = "Hurmatli " + fullName + "!\n\n" +
                "Sizni ushbu maxsus kun bilan chin qalbimdan tabriklayman!\n" +
                "Hayotingiz iloji boricha yorqin daqiqalar, yaxshi uchrashuvlar va iliqlik bilan to'ldirilsin. Har bir yangi kun sizga quvonch, o'ziga ishonch va yangi yutuqlar uchun ilhom olib kelishini tilayman.\n\n" +
                "Sizning muvaffaqiyatlaringizni qo'llab-quvvatlashga va quvonishga tayyor bo'lgan yaqin odamlar doimo bo'lsin. Qalbingizda totuvlik, qalbingizda tinchlik, boshingizda yorqin fikrlar va katta orzular bo'lsin.\n" +
                "Va siz chin dildan orzu qilgan hamma narsa amalga oshsin - oson, o'z vaqtida va hatto siz kutganingizdan ham yaxshiroq!\n\n" +
                "Sizga salomatlik, ichki kuch, barqarorlik va haqiqiy baxt. Yorqin yashang, dadil olg'a boring va biling: eng yaxshisi endi boshlanmoqda!\n\n" +
                "Bayramingiz muborak! \uD83C\uDF1F";

        exec(new SendMessage(user.getId()+"", text));
    }
}
