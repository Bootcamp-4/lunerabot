package com.lunerabot.LuneraBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramBot extends TelegramLongPollingBot {
	
	private List<Long> chatIds = new ArrayList<>();
	private String currentWinners;

	public void updateScores(String winners) {
		for (Long chatId : chatIds) {
			SendMessage message = SendMessage.builder().chatId(chatId).text(winners).build();

			try {
				execute(message);
				currentWinners = winners;
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onUpdateReceived(Update update) {
		// Se obtiene el mensaje escrito por el usuario
		final String messageTextReceived = update.getMessage().getText();

		// Se obtiene el id de chat del usuario
		final long chatId = update.getMessage().getChatId();
		if (!chatIds.contains(chatId)) {
			chatIds.add(chatId);
		}
		
		// Se crea un objeto mensaje
		String text = "Welcome to LuneraScoreBot! We will update you when scores of Solera's Bootcamp 4 change.";
		SendMessage message = SendMessage.builder().chatId(chatId).text(text).build();

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
