package blackjack.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import blackjack.domain.card.Deck;
import blackjack.domain.dto.OutcomeDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameBoardTest {

    /**
     * 카드 순서 :
     * Ace - HEART, SPADE, CLOVER, DIAMOND
     * 2 - HEART, SPADE, CLOVER, DIAMOND
     * ...
     * KING - HEART, SPADE, CLOVER, DIAMOND
     */
    @DisplayName("딜러의 최종 승패를 알려준다.")
    @Test
    void informDealerOutcome() {
        final Deck deck = new Deck(new TestDeckFactory());
        final Players players = Players.from(List.of("pobi"));
        final Dealer dealer = Dealer.create();
        final Referee referee = new Referee(dealer.getCards());
        final GameBoard gameBoard = new GameBoard(deck, dealer, players);
        gameBoard.drawInitialPlayersCards();
        gameBoard.drawInitialDealerCards();

        final List<Outcome> dealerOutcome = gameBoard.getDealerOutcome(referee);

        assertThat(dealerOutcome).containsExactly(Outcome.PUSH);
    }

    @DisplayName("모든 플레이어의 최종 승패를 알려준다.")
    @Test
    void informPlayersOutcome() {
        final Deck deck = new Deck(new TestDeckFactory());
        final Players players = Players.from(List.of("pobi"));
        final Dealer dealer = Dealer.create();
        final Referee referee = new Referee(dealer.getCards());
        final GameBoard gameBoard = new GameBoard(deck, dealer, players);
        gameBoard.drawInitialPlayersCards();
        gameBoard.drawInitialDealerCards();

        final List<OutcomeDto> playerOutcomeDtos = gameBoard.getPlayerOutcomeDtos(referee);
        final Name name = playerOutcomeDtos.get(0).getName();
        final Outcome outcome = playerOutcomeDtos.get(0).getOutcome();

        assertAll(
                () -> assertThat(playerOutcomeDtos.size()).isEqualTo(players.getPlayers().size()),
                () -> assertThat(name).isEqualTo(new Name("pobi")),
                () -> assertThat(outcome).isEqualTo(Outcome.PUSH)
        );
    }
}
