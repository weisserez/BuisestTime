import com.credorax.Solution;
import org.junit.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class SolutionTest {

    @Test
    public void test_LogicForArray(){

        Solution solution = new Solution();
        long busiestTime = solution.findBusiestTime(getClass().getClassLoader().getResource("test.txt").getPath());
        then(busiestTime).isEqualTo(1);
    }

    @Test
    public void test2_LogicForArray(){

        Solution solution = new Solution();
        long busiestTime = solution.findBusiestTime(getClass().getClassLoader().getResource("test2.txt").getPath());
        then(busiestTime).isEqualTo(5);
    }

}
