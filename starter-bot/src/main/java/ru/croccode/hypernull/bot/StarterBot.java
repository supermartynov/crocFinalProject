package ru.croccode.hypernull.bot;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

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

	private static final Random rnd = new Random(System.currentTimeMillis());

	private final MatchMode mode;

	private Offset moveOffset;

	private int moveCounter = 0;

	private InitiallyDataObject dataObject;

	private UpdateDataObject updateDataObject;

	private BasicMove basicMove;

	public StarterBot(MatchMode mode) {
		this.mode = mode;
	}

	@Override
	public Register onHello(Hello hello) {
		Register register = new Register();
		register.setMode(mode);
		register.setBotName("starter-bot");
		return register;
	}

	@Override
	public void onMatchStarted(MatchStarted matchStarted) {
		int width = matchStarted.getMapSize().width();
		int height = matchStarted.getMapSize().height();
		int id = matchStarted.getYourId();
		int viewRadius = matchStarted.getViewRadius();
		int miningRadius = matchStarted.getMiningRadius();
		System.out.println(width);
		System.out.println(height);
		updateDataObject = new UpdateDataObject();
		dataObject = new InitiallyDataObject(width, height, id, viewRadius, miningRadius);
	}

	@Override
	public Move onUpdate(Update update) {
		//System.out.println(updateDataObject.getBlocks());
		System.out.println(updateDataObject.getYourPosition());
		updateDataObject.resetData(update, dataObject.getBotId());
		basicMove = new BasicMove(updateDataObject, dataObject);
		moveOffset = basicMove.go();

		moveCounter++;
		Move move = new Move();
		move.setOffset(moveOffset);
		return move;
	}

	@Override
	public void onMatchOver(MatchOver matchOver) {

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

}
