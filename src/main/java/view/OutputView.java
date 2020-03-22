package view;

import domain.card.Card;
import domain.card.Cards;
import domain.card.Deck;
import domain.result.PlayerResult;
import domain.user.Dealer;
import domain.user.Player;
import domain.user.Players;
import domain.user.User;

import java.util.ArrayList;
import java.util.List;

public class OutputView {
    static void printEnterPlayerNames() {
        System.out.println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)");
    }

    public static void printFirstCardDealt(Dealer dealer, Players players) {
        List<String> playerNames = players.getNames();
        System.out.printf("딜러와 %s 에게 %d장의 카드를 나누어주었습니다.\n\n",
                String.join(", ", playerNames),
                Deck.NUMBER_OF_FIRST_DEAL_CARDS
        );
        printAnyDealerCard(dealer);
        printPlayersCards(players);
    }

    private static void printPlayersCards(Players players) {
        for (Player player : players.getPlayers()) {
            printPlayerCards(player);
            System.out.println();
        }
    }

    public static void printPlayerCards(Player player) {
        printUserCards(player.getName().getName(), player);
    }

    private static void printUserCards(String name, User user) {
        System.out.print(name + "카드 : "
                + formatCardStatus(user.getCards().getCards())
        );
    }

    private static void printAnyDealerCard(Dealer dealer) {
        Card anyDealerCard = dealer.getAnyCard();
        System.out.println("딜러: " + anyDealerCard.getSymbolName() + anyDealerCard.getTypeKorean());
    }

    private static String formatCardStatus(List<Card> cards) {
        List<String> cardState = new ArrayList<>();
        for (Card card : cards) {
            cardState.add(card.getSymbolName() + card.getTypeKorean());
        }
        return String.join(", ", cardState);
    }

    static void printAskWantMoreCard(String name) {
        System.out.println();
        System.out.println(name + "님은 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)");
    }

    public static void printDealerHasReceivedMoreCard() {
        System.out.printf("딜러는 %d 이하라 한장의 카드를 더 받았습니다.\n", Cards.MAX_SUM_FOR_DEALER_MORE_CARD);
        System.out.println();
    }

    public static void printFinalCardStatus(Dealer dealer, Players players) {
        printDealerCard(dealer);
        System.out.println(" - " + "결과 : " + dealer.getTotalScore());

        for (Player player : players.getPlayers()) {
            printPlayerCards(player);
            System.out.println(" - " + "결과 : " + player.getTotalScore());
        }
        System.out.println();
    }

    private static void printDealerCard(Dealer dealer) {
        System.out.println();
        printUserCards("딜러", dealer);
    }

    public static void printResultMessage() {
        System.out.println("## 최종 수익");
    }

    public static void printExceptionMessage(Exception e) {
        System.out.println(e.getMessage());
    }

    public static void printEnterBettingMoney(String name) {
        System.out.println(name + "의 배팅금액은?");
    }

    public static void printDealerResult(double dealerResult) {
        System.out.printf("딜러 : %.0f", dealerResult);
        System.out.println();
    }

    public static void printPlayerResult(PlayerResult playerResult) {
        System.out.printf("%s : %.0f", playerResult.getName().getName(), playerResult.getResultMoney().getResultMoney());
        System.out.println();
    }
}
