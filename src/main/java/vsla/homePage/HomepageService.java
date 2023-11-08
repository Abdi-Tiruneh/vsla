package vsla.homePage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vsla.userManager.user.Users;
import vsla.utils.CurrentlyLoggedInUser;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HomepageService {
    private final CurrentlyLoggedInUser loggedInUser;

    public HomepageResponse getHomepageResponse() {
        Users user = loggedInUser.getUser();


        List<RecentContribution> recentContributions = createRandomContributions();
        double totalAmount = calculateTotalAmount(recentContributions);

        HomepageResponse homepageResponse = new HomepageResponse();
        homepageResponse.setGroupName(user.getGroup().getGroupName());
        homepageResponse.setTotalAmount(totalAmount);
        homepageResponse.setMilestone(milestone());
        homepageResponse.setRecentContributions(recentContributions);
        homepageResponse.setTipOfTheDay(tipOfTheDay());


        return homepageResponse;
    }


    private TipOfTheDay tipOfTheDay() {
        TipOfTheDay tipOfTheDay = new TipOfTheDay();
        tipOfTheDay.setTitle("Malaria Prevention Tip");
        tipOfTheDay.setDescription("Use mosquito nets at night to protect yourself from malaria-carrying mosquitoes.");

        return tipOfTheDay;
    }

    private Milestone milestone() {
        return new Milestone(2, 2, 1, 0);
    }


    // Ethiopians' first names
    private static String[] ethiopianFirstNames = {
            "Faaruu", "Mulu", "Biftu", "Genet", "Malkam",
            "Caalaa", "Tesfaye", "Sanyii", "Dawit", "Tulluu"
    };

    // Ethiopians' last names
    private static String[] ethiopianLastNames = {
            "Mengistu", "Ejersa", "Girma", "Waariyo", "Tulama"
    };

    // Method to create a list of 10 instances with random data
    private List<RecentContribution> createRandomContributions() {
        List<RecentContribution> contributions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String randomFirstName = ethiopianFirstNames[random.nextInt(ethiopianFirstNames.length)];
            String randomLastName = ethiopianLastNames[random.nextInt(ethiopianLastNames.length)];
            String randomContributor = randomFirstName + " " + randomLastName;
            LocalDate randomDate = LocalDate.now().minusDays(random.nextInt(30));
            double randomAmount = 50 + (int) (random.nextDouble() * 952);
            contributions.add(new RecentContribution(randomContributor, randomDate, randomAmount));
        }

        // Sort the contributions by date in descending order (latest at the top)
        Collections.sort(contributions, Comparator.comparing(RecentContribution::getDate).reversed());
        return contributions;
    }

    // Method to calculate the sum of total amount from a list of contributions
    private int calculateTotalAmount(List<RecentContribution> contributions) {
        int totalAmount = 0;
        for (RecentContribution contribution : contributions)
            totalAmount += contribution.getAmount();

        return totalAmount;
    }

}





