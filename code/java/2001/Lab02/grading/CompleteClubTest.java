package grading;
import club.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GradingTestWatcher.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@GradeValue(10)
class CompleteClubTest {

    private String[] testData = {
            "Bob Hopkins,4,2004",
            "Tom Matudio,6,2021",
            "Maria Halftrack,6,2012",
            "Lon Critos,8,2010",
            "Ron Howard,7,2112",
            "Sal Penwick,4,2004",
            "Terry Hogwark,7,2012"
    };
    Club testClub;

    @BeforeEach
    void setUp() {
        testClub = new Club();
    }

    private void loadData(){
        for( String line: testData ){
            String[] elements = line.split(",");
            testClub.join(new Membership(elements[0],Integer.parseInt(elements[1]),Integer.parseInt(elements[2])));
        }
    }
    @AfterEach
    void tearDown() {
    }

    @GradeValue(1)
    @Test
    public void join() {
        loadData();
        for( String line: testData ){
            String[] elements = line.split(",");
            assertNotNull(testClub.find(elements[0]));
        }
    }

    @GradeValue(1)
    @Test
    public void numberOfMembers() {
        loadData();
        assertEquals(testData.length,testClub.numberOfMembers());
    }

    @GradeValue(1)
    @Test
    public void joinedInMonth() {
        loadData();
        assertEquals(2, testClub.joinedInMonth(4));
    }

    @GradeValue(1)
    @Test
    public void purge() {
        loadData();
        int oldcount = testClub.numberOfMembers();
        ArrayList<Membership> out = testClub.purge(4, 2004);
        assertEquals(out.size(), oldcount-testClub.numberOfMembers(), "Purging did not remove the correct number");
    }

    @GradeValue(1)
    @Test
    public void find(){
        loadData();
        assertNotNull(testClub.find("Terry Hogwark"));
        assertNull(testClub.find("Bogus Member"));
    }

    @GradeValue(1)
    @Test
    public void findBeforeAfter(){

        assertNull(testClub.find("Terry Hogwark"));
        assertNull(testClub.find("Bogus Member"));
        loadData();
        assertNotNull(testClub.find("Terry Hogwark"));
        assertNull(testClub.find("Bogus Member"));

    }

    @GradeValue(1)
    @Test
    public void joinAgain(){

        loadData();
        int n = testClub.numberOfMembers();
        testClub.join(new Membership("Terry Hogwark", 5, 2004) );
        assertEquals(n, testClub.numberOfMembers(), "Can't joint twice");
    }


    @GradeValue(1)
    @Test
    public void invalidMonth(){
        loadData();
        assertEquals(0, testClub.joinedInMonth(15));

    }

    @GradeValue(1)
    @Test
    public void purgeOne() {
        loadData();
        int oldcount = testClub.numberOfMembers();
        ArrayList<Membership> out = testClub.purge(4, 2004);
        assertEquals(out.size(), oldcount-testClub.numberOfMembers(), "Purging did not remove the correct number");
        assertNull(testClub.find("Bob Hopkins"),"Bob was not purged correctly");
    }



    @GradeValue(1)
    @Test
    public void joinSame(){

        loadData();
        int n = testClub.numberOfMembers();
        Membership holly = new Membership("Holly Barker", 5, 2004);
        testClub.join(holly);
        assertEquals(n+1, testClub.numberOfMembers(), "Couldn't join the club");
        testClub.join(holly);
        assertEquals(n+1, testClub.numberOfMembers(), "Don't join twice!");
    }
}
