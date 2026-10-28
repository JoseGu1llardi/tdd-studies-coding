import java.util.HashSet;
import java.util.Set;

public class MemberLoans {
    private static final int MAX_BORROWED_BOOKS = 3;

    private final Set<String> borrowedIsbns = new HashSet<>();

    public void borrow(String isbnCode) {
        validateIsbn(isbnCode);

        if (borrowedIsbns.size() >= MAX_BORROWED_BOOKS) {
            throw new IllegalStateException("Cannot borrow more than " + MAX_BORROWED_BOOKS + " books at a time.");
        }
        if (!borrowedIsbns.add(isbnCode)) {
            throw new IllegalStateException("Cannot borrow the same book twice " + isbnCode);
        }
    }

    public void giveBack(String isbnCode) {
        validateIsbn(isbnCode);

        if (!borrowedIsbns.remove(isbnCode)) {
            throw new IllegalStateException("Cannot return a book that is not currently borrowed: " + isbnCode);
        }
    }

    public int borrowedCount() {
        return borrowedIsbns.size();
    }

    private void validateIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be null or blank.");
        }
    }
}
