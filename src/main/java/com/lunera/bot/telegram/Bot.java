package com.lunera.bot.telegram;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class Bot extends TelegramLongPollingBot {
	
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
		// Bot gets message written by the user
		final String messageTextReceived = update.getMessage().getText();

		// Bot gets users chatId
		final long chatId = update.getMessage().getChatId();
		if (!chatIds.contains(chatId)) {
			chatIds.add(chatId);
		}

		String text = "Welcome to LuneraScoreBot! We will update you when scores of Solera's Bootcamp 4 change.";
		SendMessage message = SendMessage.builder().chatId(chatId).text(text).build();

		try {
			// Message is sent to users
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return "LuneraDevBot";
		// return "LuneraScoreBot";
	}

	@Override
	public String getBotToken() {
		return "5677874207:AAESJ7DdhGX4acHwoSjKCDIs3fUecsX_SDo";
		// return "5615146806:AAHuoaSYJnBk9R8HjAZYQARbeynNsp4E_xU";
	}
}
