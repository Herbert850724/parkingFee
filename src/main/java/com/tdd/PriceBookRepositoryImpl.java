package com.tdd;


public class PriceBookRepositoryImpl implements PriceBookRepository {

    private PriceBook priceBook;

    @Override
    public PriceBook getPriceBook() {
        return priceBook;
    }

    public void setPriceBook(PriceBook priceBook) {
        this.priceBook = priceBook;
    }

    public PriceBookRepositoryImpl(){

    }
    public PriceBookRepositoryImpl(PriceBook priceBook){
        this.priceBook = priceBook;
    }
}
