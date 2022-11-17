package com.lunerabot.LuneraBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBoot extends TelegramLongPollingBot {
	
	private Long userChatId;

	public void updateScores(String winners) {
		SendMessage message = SendMessage.builder().chatId(userChatId).text(winners).build();
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onUpdateReceived(Update update) {
		// Se obtiene el mensaje escrito por el usuario
		final String messageTextReceived = update.getMessage().getText();

		System.out.println("Escribieron en el bot " + messageTextReceived);
		int[] numeros = { 1, 2, 3, 4, 5, 6, 7 };
		// Se obtiene el id de chat del usuario
		final long chatId = update.getMessage().getChatId();
		userChatId = chatId;
		
		// Se crea un objeto mensaje
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		String s = "";
		for (int i = 0; i < numeros.length; i++) {
			s += numeros[i] + "\n";
		}
		message.setText("Gracias por escribirnos fiera, mastodonte, CRACK");
		// message.setText(s);
		try {
			// Se envía el mensaje
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getBotUsername() {
		// PROD Bot
		// return "LuneraScoreBot";
		// DEV Bot
		return "LuneraDevBot";
	}

	@Override
	public String getBotToken() {
		// PROD Bot
		// return "5615146806:AAHuoaSYJnBk9R8HjAZYQARbeynNsp4E_xU";
		// DEV Bot
		return "5784608604:AAHGwrv2LQiLw2xFewgkSd0TLjkj1Ok9yz8";
	}
}
