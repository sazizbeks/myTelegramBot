package controllers;

import domain.CurrencyParser;
import domain.ExchangeRates;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class BotController extends TelegramLongPollingBot {
    private final String USERNAME = "aziz_currency_checker_bot";
    private final String TOKEN = "1214761737:AAG0R-vpo02qYF70SCGNWCojNtnMCA7qnyY";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message receivedMessage = update.getMessage();
            User messageSender = receivedMessage.getFrom();

            if (receivedMessage.getText().equals("/start")) {
                String welcomeMessage = "Welcome, " + messageSender.getFirstName() + ' ' + messageSender.getLastName()
                        + "\nThis bot can give you information on the exchange rate of various currencies in tenge"
                        + "\n" + help();
                try {
                    execute(new SendMessage()
                            .setText(welcomeMessage)
                            .setChatId(receivedMessage.getChatId())
                            .setReplyMarkup(getInlineKeyboardMessage()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (receivedMessage.getText().equals("/buttons")) {
                try {
                    execute(new SendMessage()
                            .setText("Buttons")
                            .setChatId(receivedMessage.getChatId())
                            .setReplyMarkup(getInlineKeyboardMessage()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (receivedMessage.getText().equals("/help")) {
                try {
                    execute(new SendMessage()
                            .setText(help())
                            .setChatId(receivedMessage.getChatId()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            try {
                execute(new SendMessage()
                        .setText(update.getCallbackQuery().getData())
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    private InlineKeyboardMarkup getInlineKeyboardMessage() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> table = new ArrayList<>();

        List<InlineKeyboardButton> buttonRow = new ArrayList(3);
        buttonRow.add(new InlineKeyboardButton().setText("Ruble").setCallbackData(CurrencyParser.converter(ExchangeRates.RUB_KZT)));
        buttonRow.add(new InlineKeyboardButton().setText("Dollar").setCallbackData(CurrencyParser.converter(ExchangeRates.USD_KZT)));
        buttonRow.add(new InlineKeyboardButton().setText("Euro").setCallbackData(CurrencyParser.converter(ExchangeRates.EUR_KZT)));

        table.add(buttonRow);
        inlineKeyboardMarkup.setKeyboard(table);
        return inlineKeyboardMarkup;
    }

    private String help() {
        return "/start - start the bot.\n" +
                "/buttons - get buttons.\n" +
                "/help - commands of this bot.";
    }
}
