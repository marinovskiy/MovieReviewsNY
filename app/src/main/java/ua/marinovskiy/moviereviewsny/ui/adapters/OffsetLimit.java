package ua.marinovskiy.moviereviewsny.ui.adapters;

public class OffsetLimit {

    private int offset;

    private int limit;

    public OffsetLimit(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "offset=" + offset + " limit=" + limit;
    }
}
