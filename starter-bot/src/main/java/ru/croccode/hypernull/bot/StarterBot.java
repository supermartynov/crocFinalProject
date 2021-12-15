package ru.croccode.hypernull.bot;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import ru.croccode.hypernull.bot.game_data.InitiallyDataObject;
import ru.croccode.hypernull.bot.game_data.UpdateDataObject;
import ru.croccode.hypernull.bot.move.BasicMove;
import ru.croccode.hypernull.domain.MatchMode;
import ru.croccode.hypernull.geometry.Offset;
import ru.croccode.hypernull.io.SocketSession;
import ru.croccode.hypernull.message.Hello;
import ru.croccode.hypernull.message.MatchOver;
import ru.croccode.hypernull.message.MatchStarted;
import ru.croccode.hypernull.message.Move;
import ru.croccode.hypernull.message.Register;
import ru.croccode.hypernull.message.Update;

public class StarterBot implements Bot {

	private final MatchMode mode;

	private Offset moveOffset;

	private int moveCounter = 0;

	private InitiallyDataObject initiallyDataObject;

	private UpdateDataObject updateDataObject;

	private BasicMove basicMove;

	public StarterBot(MatchMode mode) {
		this.mode = mode;
	}

	@Override
	public Register onHello(Hello hello) {
		Register register = new Register();
		register.setMode(mode);
		register.setBotName("Martynov");
		return register;
	}

	@Override
	public void onMatchStarted(MatchStarted matchStarted) {
		updateDataObject = new UpdateDataObject();
		initiallyDataObject = new InitiallyDataObject(matchStarted);
	}

	@Override
	public Move onUpdate(Update update) {
		updateDataObject.resetData(update, initiallyDataObject.getBotId());
		basicMove = new BasicMove(updateDataObject, initiallyDataObject);
		moveOffset = basicMove.go();
		moveCounter++;
		Move move = new Move();
		move.setOffset(moveOffset);
		return move;
	}

	@Override
	public void onMatchOver(MatchOver matchOver) {
		printHistoryMap();
	}

	public static void main(String[] args) throws IOException {
		Socket socket = new Socket();
		socket.setTcpNoDelay(true);
		socket.setSoTimeout(300_000);
		socket.connect(new InetSocketAddress("localhost", 2021));

		SocketSession session = new SocketSession(socket);
		StarterBot bot = new StarterBot(MatchMode.FRIENDLY);
		new BotMatchRunner(bot, session).run();
	}

	public void printHistoryMap() {
		System.out.println("Карта:");
		String tmp = "";
		for (int i = 0; i < initiallyDataObject.getMapWidth(); i++) {
			for (int j = 0; j < initiallyDataObject.getMapHeight(); j++) {
				switch (initiallyDataObject.getPointHistoryArray()[i][j]) {
					case NOT_VISITED:
						tmp = "X";
						break;
					case VISITED:
						tmp = "V";
						break;
					case COIN:
						tmp = "C";
						break;
					case BlOCK:
						tmp = "B";
				}
				System.out.print(tmp + " ");
			}
			System.out.println();
		}
	}
}
