package in.pmpavan.app.cointoss;

import java.util.Random;

/**
 * Created by Pavan on 07/05/17.
 */

public class CoinTossController {

    private final CoinTossHelper coinTossHelper;
    private static CoinTossController coinTossController;


    public interface CoinTossHelper {
        void showResult(CoinFace coinFace);
    }

    public enum CoinFace {
        HEADS(0),
        TAILS(1);
        private int coinFace;

        CoinFace(int face) {
            this.coinFace = face;
        }

        public int getCoinState() {
            return coinFace;
        }
    }

    CoinTossController(CoinTossHelper coinTossHelper) {
        this.coinTossHelper = coinTossHelper;
    }

    public static CoinTossController getInstance(CoinTossHelper context) {
        if (coinTossController == null) {
            coinTossController = new CoinTossController(context);
        }
        return coinTossController;
    }

    public void performButtonClick() {
        Random rand = new Random();
        int random = rand.nextInt(2);

        CoinFace coinFace = CoinFace.HEADS;
        if (random == CoinFace.TAILS.getCoinState()) {
            coinFace = CoinFace.TAILS;
        }
        coinTossHelper.showResult(coinFace);
    }

    public int getImgRes(CoinFace coinFace) {
        int imgRes = R.drawable.heads;
        if (coinFace == CoinFace.TAILS) {
            imgRes = R.drawable.tails;
        }
        return imgRes;
    }

    public int getTextRes(CoinFace coinFace) {
        int txtRes = R.string.heads;
        if (coinFace == CoinFace.TAILS) {
            txtRes = R.string.tails;
        }
        return txtRes;
    }

}
