import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemberLoansTest {
    private MemberLoans member;

    @BeforeEach
    void setup() {
        member = new MemberLoans();
    }

    @Test
    void newMemberStartsWithZeroBorrowedBooks() {
        assertEquals(0, member.borrowedCount());
    }

    @Test
    void borrowingBookIncreasesCounter() {
        member.borrow("12345");

        assertEquals(1, member.borrowedCount());
    }

    @Test
    void returningBookDecreasesCounter() {
        member.borrow("12345");
        member.borrow("54321");

        member.giveBack("12345");
        assertEquals(1, member.borrowedCount());
    }

    @Test
    void borrowingSameBookTwiceThrows() {
        member.borrow("12345");

        assertThrows(IllegalStateException.class, () -> member.borrow("12345"));
    }

    @Test
    void returningBookNotBorrowedThrows() {
        member.borrow("12345");

        assertThrows(IllegalStateException.class, () -> member.giveBack("54321"));
    }

    @Test
    void failedReturnDoesNotChangeTheCount() {
        member.borrow("12345");

        assertThrows(IllegalStateException.class, () -> member.giveBack("54321"));
        assertEquals(1, member.borrowedCount());
    }

    @Test
    void returningBookTwiceThrows() {
        member.borrow("12345");
        member.borrow("54321");
        member.giveBack("12345");

        assertThrows(IllegalStateException.class, () -> member.giveBack("12345"));
    }

    private void borrowUpToTheLimit() {
        member.borrow("12345");
        member.borrow("54321");
        member.borrow("67890");
    }

    @Test
    void borrowingFourthBookThrows() {
        borrowUpToTheLimit();

        assertThrows(IllegalStateException.class, () -> member.borrow("00000"));
    }

    @Test
    void failedBorrowDoesNotChangeTheCount() {
        borrowUpToTheLimit();

        assertThrows(IllegalStateException.class, () -> member.borrow("00000"));
        assertEquals(3, member.borrowedCount());
    }

    @Test
    void returningABookAllowsBorrowingAgainWhenAtLimit() {
        borrowUpToTheLimit();

        member.giveBack("12345");

        member.borrow("00000");

        assertEquals(3, member.borrowedCount());
    }

    @Test
    void borrowingTheSameBookAgainAfterReturningItSucceeds() {
        member.borrow("12345");
        member.giveBack("12345");
        member.borrow("12345");

        assertEquals(1, member.borrowedCount());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "   " })
    void borrowingWithInvalidIsbnThrows(String isbnCode) {
        assertThrows(IllegalArgumentException.class, () -> member.borrow(isbnCode));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "   " })
    void returningWithInvalidIsbnThrows(String isbnCode) {
        assertThrows(IllegalArgumentException.class, () -> member.giveBack(isbnCode));
    }
}
