package blackjack.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Players {

    public static final int INITIAL_CARD_COUNT = 2;
    private final List<Player> players;

    private Players(final List<Player> players) {
        this.players = players;
    }

    public static Players from(final List<String> names) {
        final List<Player> players = new ArrayList<>();
        for (final String name : names) {
            players.add(Player.from(name));
        }
        return new Players(players);
    }

    public void drawInitialHand(final Dealer dealer) {
        for (final Player player : players) {
            drawInitialHand(dealer, player);
        }
    }

    private static void drawInitialHand(final Dealer dealer, final Player player) {
        for (int i = 0; i < INITIAL_CARD_COUNT; i++) {
            player.draw(dealer.drawPlayerCard());
        }
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }
}
