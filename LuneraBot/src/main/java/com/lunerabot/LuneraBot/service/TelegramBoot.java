package com.lunerabot.LuneraBot.service;

import java.util.Iterator;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBoot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // Se obtiene el mensaje escrito por el usuario
        final String messageTextReceived = update.getMessage().getText();

        System.out.println("Escribieron en el bot " + messageTextReceived);
        int[] numeros = {1,2,3,4,5,6,7};
        // Se obtiene el id de chat del usuario
        final long chatId = update.getMessage().getChatId();

        // Se crea un objeto mensaje
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String s ="";
        for (int i = 0; i < numeros.length; i++) {
			s+=numeros[i]+"\n";
		}
        message.setText("Gracias por escribirnos fiera, mastodonte, CRACK");
        //message.setText(s);
        try {
            // Se envía el mensaje
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getBotUsername() {
        return "LuneraScoreBot";
    }

    @Override
    public String getBotToken() {
        return "5615146806:AAHuoaSYJnBk9R8HjAZYQARbeynNsp4E_xU";
    }
}
