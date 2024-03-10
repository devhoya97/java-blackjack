package blackjack.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Players {

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

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public List<Name> getNames() {
        final List<Name> names = new ArrayList<>();
        for (final Player player : players) {
            names.add(player.getName());
        }
        return names;
    }

    public Player findByName(final Name name) {
        return players.stream()
                .filter(player -> Objects.equals(player.getName(), name))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("해당 이름을 가진 플레이어가 존재하지 않습니다."));
    }
}
