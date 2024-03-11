package blackjack;

import blackjack.domain.Dealer;
import blackjack.domain.GameBoard;
import blackjack.domain.Outcome;
import blackjack.domain.Player;
import blackjack.domain.Players;
import blackjack.domain.Referee;
import blackjack.domain.card.Cards;
import blackjack.domain.card.Deck;
import blackjack.domain.card.DeckShuffleFactory;
import blackjack.dto.DealerDto;
import blackjack.dto.OutcomeDto;
import blackjack.dto.OutcomesDto;
import blackjack.dto.PlayerDto;
import blackjack.view.InputView;
import blackjack.view.OutputView;
import java.util.List;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        final GameBoard gameBoard = createGameBoard();
        drawInitialCards(gameBoard);

        hit(gameBoard);
        OutputView.printFinalState(createDealerDto(gameBoard.getDealerCards()),
                toDtos(gameBoard.getPlayers()));

        Referee referee = new Referee(gameBoard.getDealerCards());
        final OutcomesDto dealerOutcome = createOutcomesDto(gameBoard.getDealerOutcomes(referee));
        final List<OutcomeDto> playerOutcomes = gameBoard.getPlayerOutcomeDtos(referee);
        OutputView.printFinalOutcomes(dealerOutcome, playerOutcomes);
    }

    private static GameBoard createGameBoard() {
        Deck deck = new DeckShuffleFactory().create();
        Dealer dealer = Dealer.create();
        Players players = Players.from(InputView.readPlayerNames());
        return new GameBoard(deck, dealer, players);
    }

    private static void drawInitialCards(GameBoard gameBoard) {
        final DealerDto dealerDto = createDealerDto(gameBoard.drawInitialDealerCards());
        final List<PlayerDto> playersDto = toDtos(gameBoard.drawInitialPlayersCards());

        OutputView.printInitialState(dealerDto, playersDto);
    }

    private static DealerDto createDealerDto(final Cards cards) {
        return new DealerDto(cards.toList(), cards.calculateOptimalSum());
    }

    private static List<PlayerDto> toDtos(final Players players) {
        return players.getPlayers().stream()
                .map(Application::toDto)
                .toList();
    }

    private static PlayerDto toDto(final Player player) {
        return new PlayerDto(player.getName(), player.getCards().toList(), player.getCards().calculateOptimalSum());
    }

    private static void hit(GameBoard gameBoard) {
        hitPlayers(gameBoard);
        OutputView.printLineSeparator();
        hitDealer(gameBoard);
        OutputView.printLineSeparator();
    }

    private static void hitPlayers(final GameBoard gameBoard) {
        final Players players = gameBoard.getPlayers();
        for (Player player : players.getPlayers()) {
            hitPlayer(gameBoard, player);
        }
    }

    private static void hitPlayer(final GameBoard gameBoard, final Player player) {
        while (gameBoard.isHit(player) && InputView.readDoesWantHit(player.getName())) {
            gameBoard.hit(player);
            OutputView.printCurrentState(toDto(player));
        }
    }

    private static void hitDealer(final GameBoard gameBoard) {
        final Dealer dealer = gameBoard.getDealer();
        while (gameBoard.isHit(dealer)) {
            gameBoard.hit(dealer);
            OutputView.printDealerDrawMessage();
        }
    }

    private static OutcomesDto createOutcomesDto(final List<Outcome> outcomes) {
        final Map<Outcome, Long> outcomeCounts = Outcome.countByKind(outcomes);
        return new OutcomesDto(
                outcomeCounts.getOrDefault(Outcome.WIN, 0L).intValue(),
                outcomeCounts.getOrDefault(Outcome.LOSE, 0L).intValue(),
                outcomeCounts.getOrDefault(Outcome.PUSH, 0L).intValue()
        );
    }
}
