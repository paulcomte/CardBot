package fr.rqndomhax.cardbot.database.requests;

public enum Requests {
    BURN(BurnRequest.class),
    TRADE(TradeRequest.class),
    AUCTION(AuctionRequest.class);

    private final Class<? extends Request> request;

    Requests(Class<? extends Request> request) {
        this.request = request;
    }

    public Class<? extends Request> getRequest() {
        return request;
    }

}