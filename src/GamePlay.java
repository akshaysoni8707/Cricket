import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

class GamePlay implements Serializable {
    public static final long uid = 1L;
    private static Player bowler = new Player();
    private static Player batsman1 = new Player();
    private static Player batsman2 = new Player();
    private int battingTeam, bowlingteam;
    private Team myTeams[] = new Team[2];
    private Team india = new Team();
    private Team australia = new Team();
    private Random random = new Random();
    private String over = "0.0";
    private int balls = 1;
    private int runs;
    private int wickets;
    private int extra;
    private int target;
    private FileStreamDemo fileStreamDemo = new FileStreamDemo();

    private void teamCreator() {
        for (int i = 0; i < myTeams.length; i++) {
            this.myTeams[i] = new Team("Team-" + (i + 1));
        }
    }

    private void reset() {
        for (int i = 0; i < myTeams[0].teamPlayers.length; i++) {
            myTeams[0].teamPlayers[i].bowledOver = false;
            myTeams[0].teamPlayers[i].onStrike = false;
            myTeams[0].teamPlayers[i].pavillion = true;
            myTeams[0].teamPlayers[i].outInMatch = false;
            myTeams[1].teamPlayers[i].bowledOver = false;
            myTeams[1].teamPlayers[i].onStrike = false;
            myTeams[1].teamPlayers[i].pavillion = true;
            myTeams[1].teamPlayers[i].outInMatch = false;
        }
    }

    private void matchesPlayed() {
        myTeams[0].matches++;
        myTeams[1].matches++;
        for (int i = 0; i < myTeams[0].teamPlayers.length; i++) {
            myTeams[0].teamPlayers[i].matchesPlayed++;
            myTeams[1].teamPlayers[i].matchesPlayed++;
        }
    }

    void display() {
        teamCreator();
 /*       fileStreamDemo.transferObject(myTeams[0], new File("C:\\Users\\Madhu\\IdeaProjects\\JavaProjects\\cricket\\src\\India.txt"));
        fileStreamDemo.transferObject(myTeams[1], new File("C:\\Users\\Madhu\\IdeaProjects\\JavaProjects\\cricket\\src\\Australia.txt"));*/
        india = fileStreamDemo.readObject(new File("C:\\Users\\Madhu\\IdeaProjects\\JavaProjects\\cricket\\src\\India.txt"));
        australia = fileStreamDemo.readObject(new File("C:\\Users\\Madhu\\IdeaProjects\\JavaProjects\\cricket\\src\\Australia.txt"));
        myTeams[0] = india;
        myTeams[1] = australia;
        matchesPlayed();
        toss();
        firstInning();
        secondInning();
        reset();
        System.out.println("\n\nIndia\n\n" + myTeams[0].toString() + "\n\nAustralia\n\n" + myTeams[1].toString());
        fileStreamDemo.transferObject(myTeams[0], new File("C:\\Users\\Madhu\\IdeaProjects\\JavaProjects\\cricket\\src\\India.txt"));
        fileStreamDemo.transferObject(myTeams[1], new File("C:\\Users\\Madhu\\IdeaProjects\\JavaProjects\\cricket\\src\\Australia.txt"));
    }

    private String setOvers(String whichOver) {
        String overSplit[] = whichOver.split("[.]");
        if (overSplit[1].equals("5")) {
            overSplit[0] = String.valueOf(Integer.valueOf(overSplit[0]) + 1);
            overSplit[1] = "0";
        } else {
            overSplit[1] = String.valueOf(Integer.valueOf(overSplit[1]) + 1);
        }
        whichOver = String.join(".", overSplit);
        return whichOver;
    }

    private void endOfOver() {
        String overSplit[] = over.split("[.]");
        if (Integer.valueOf(overSplit[0]) >= 1 && Integer.valueOf(overSplit[1]) == 0) {
            strikeChanged();
            newbowler();
        }
    }

    private void toss() {
        int noOfFlips, j = 0;
        int sideOfCoin = random.nextInt(2);
        myTeams[0].tossSelection = 0;
        myTeams[1].tossSelection = 1;
        noOfFlips = random.nextInt(12) + 13;
        System.out.println(sideOfCoin + "   " + noOfFlips);
        do {
            sideOfCoin = (sideOfCoin == 1) ? 0 : 1;
            j++;
        } while (j < noOfFlips);
        System.out.println(sideOfCoin);
        if (myTeams[0].tossSelection == sideOfCoin) {
            System.out.println(myTeams[0].teamName + " won toss and choose to bat");
            battingTeam = 0;
            bowlingteam = 1;
        } else {
            System.out.println(myTeams[1].teamName + " won toss and choose to bat");
            battingTeam = 1;
            bowlingteam = 0;
        }
    }

    private void openingBatsman() {
        batsman1 = myTeams[battingTeam].teamPlayers[0];
        batsman1.pavillion = false;
        batsman1.onStrike = true;
        batsman2 = myTeams[battingTeam].teamPlayers[1];
        batsman2.pavillion = false;
    }

    private void newbowler() {
        for (int i = myTeams[bowlingteam].teamPlayers.length - 1; i >= 0; i--) {
            if (!myTeams[bowlingteam].teamPlayers[i].bowledOver && !myTeams[bowlingteam].teamPlayers[i].bowledOver) {
                bowler = myTeams[bowlingteam].teamPlayers[i];
                bowler.bowledOver = true;
                break;
            }
            if (i == 6) {
                for (int j = 0; j < myTeams[bowlingteam].teamPlayers.length; j++) {
                    myTeams[bowlingteam].teamPlayers[j].bowledOver = false;
                }
            }
        }
    }

    private void newBatsman() {
        if (batsman1.onStrike) {
            batsman1.outInMatch = true;
            batsman1.pavillion = true;
            batsman1.out++;
            batsman1.onStrike = false;
            for (int i = 0; i < myTeams[battingTeam].teamPlayers.length; i++) {
                if (!myTeams[battingTeam].teamPlayers[i].outInMatch && myTeams[battingTeam].teamPlayers[i].pavillion) {
                    batsman1 = myTeams[battingTeam].teamPlayers[i];
                    batsman1.pavillion = false;
                    batsman1.onStrike = true;
                    break;
                }
            }
        }
        if (batsman2.onStrike) {
            batsman2.outInMatch = true;
            batsman2.pavillion = true;
            batsman2.out++;
            batsman2.onStrike = false;
            for (int i = 0; i < myTeams[battingTeam].teamPlayers.length; i++) {
                if (!myTeams[battingTeam].teamPlayers[i].outInMatch && myTeams[battingTeam].teamPlayers[i].pavillion) {
                    batsman2 = myTeams[battingTeam].teamPlayers[i];
                    batsman2.pavillion = false;
                    batsman2.onStrike = true;
                    break;
                }
            }
        }
    }

    private void strikeChanged() {
        if (batsman1.onStrike) {
            batsman1.onStrike = false;
            batsman2.onStrike = true;
        } else {
            batsman2.onStrike = false;
            batsman1.onStrike = true;
        }
    }

    private void firstInning() {
        openingBatsman();
        newbowler();
        do {
            playing();
        } while (balls <= 120 && wickets < 10);
        System.out.println(runs + "\\" + wickets + "      extra : " + extra);
    /*    try {
            fileStreamDemo.fileoutputstream(new File("C:\\Users\\Madhu\\IdeaProjects\\JavaProjects\\cricket\\src\\TeamStats.txt"), "Batting team\n\n" + myTeams[battingTeam].toString() + "\n\nBowling team\n\n" + myTeams[bowlingteam].toString());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        target = runs + 1;
        over = "0.0";
        balls = 0;
        runs = 0;
        wickets = 0;
        extra = 0;
        int temp = battingTeam;
        battingTeam = bowlingteam;
        bowlingteam = temp;
    }

    private void secondInning() {
        openingBatsman();
        newbowler();
        do {
            playing();
        } while (!win());
        try {
            fileStreamDemo.fileoutputstream(new File("C:\\Users\\Madhu\\IdeaProjects\\JavaProjects\\cricket\\src\\TeamStats.txt"), "India\n\n" + myTeams[0].toString() + "\n\nAustralia\n\n" + myTeams[1].toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(runs + "\\" + wickets + "      extra : " + extra);
    }

    private boolean win() {
        if ((balls == 120) && (wickets <= 10) && (runs == target - 1)) {
            System.out.println("end of overs : draw");
            myTeams[battingTeam].matchesDraw++;
            myTeams[bowlingteam].matchesDraw++;
            return true;
        }
        if ((balls <= 120) && (runs < target - 1) && (wickets == 10)) {
            System.out.println(myTeams[bowlingteam].teamName + "  won by " + ((target - runs) - 1) + " runs , " + myTeams[battingTeam].teamName + " is Allout");
            myTeams[bowlingteam].matchesWon++;
            myTeams[battingTeam].matchesLost++;
            return true;
        }
        if ((balls <= 120) && (runs == target - 1) && (wickets == 10)) {
            System.out.println("All out: draw");
            myTeams[battingTeam].matchesDraw++;
            myTeams[bowlingteam].matchesDraw++;
            return true;
        }
        if ((balls == 120) && (runs < target - 1) && (wickets <= 10)) {
            System.out.println(myTeams[bowlingteam].teamName + "  won by " + ((target - runs) - 1) + " runs :end of overs ");
            myTeams[bowlingteam].matchesWon++;
            myTeams[battingTeam].matchesLost++;
            return true;
        }
        if ((runs >= target) && (wickets < 10) && (balls <= 120)) {
            System.out.println(myTeams[battingTeam].teamName + "  won by " + (10 - wickets) + " : wickets");
            myTeams[bowlingteam].matchesLost++;
            myTeams[battingTeam].matchesWon++;
            return true;
        } else {
            return false;
        }
    }

    private void batsmanRunCount(int r) {
        if (batsman1.onStrike) {
            batsman1.runs = (batsman1.runs + r);
            batsman1.ballsPlayed++;
            batsman1.strikeRate = (( double ) batsman1.runs / batsman1.ballsPlayed) * 100;
        } else {
            batsman2.runs = (batsman2.runs + r);
            batsman2.ballsPlayed++;
            batsman2.strikeRate = (( double ) batsman2.runs / batsman2.ballsPlayed) * 100;
        }
    }

    private void tempDisp() {
        if (batsman1.onStrike) {
            System.out.println(" * " + batsman1.toString() + "\n\n");
            System.out.println(batsman2.toString() + "\n\n");
        } else {
            System.out.println(" * " + batsman2.toString() + "\n\n");
            System.out.println(batsman1.toString() + "\n\n");
        }
        System.out.println(bowler.toString() + "\n\n");
    }

    private void playing() {
        int choice;
        choice = random.nextInt(9);
        if (choice == 3 || choice == 4 || choice == 6 || choice == 5 || choice == 7) {
            choice = random.nextInt(9);
        } else if (choice == 8) {
            choice = random.nextInt(3);
        }
        switch (choice) {
            case 0:
                over = setOvers(over);
                bowler.overBowled = setOvers(bowler.overBowled);
                System.out.println("\n" + over + "\n");
                System.out.println("dot ball");
                ++balls;
                runs = runs + choice;
                batsmanRunCount(choice);
                tempDisp();
                endOfOver();
                break;
            case 1:
                over = setOvers(over);
                bowler.overBowled = setOvers(bowler.overBowled);
                System.out.println("\n" + over + "\n");
                System.out.println("single run");
                ++balls;
                runs = runs + choice;
                batsmanRunCount(choice);
                strikeChanged();
                tempDisp();
                endOfOver();
                break;
            case 2:
                over = setOvers(over);
                bowler.overBowled = setOvers(bowler.overBowled);
                System.out.println("\n" + over + "\n");
                System.out.println("two runs");
                ++balls;
                runs = runs + choice;
                batsmanRunCount(choice);
                tempDisp();
                endOfOver();
                break;
            case 3:
                over = setOvers(over);
                bowler.overBowled = setOvers(bowler.overBowled);
                System.out.println("\n" + over + "\n");
                System.out.println("three runs");
                ++balls;
                runs = runs + choice;
                batsmanRunCount(choice);
                strikeChanged();
                tempDisp();
                endOfOver();
                break;
            case 4:
                over = setOvers(over);
                bowler.overBowled = setOvers(bowler.overBowled);
                System.out.println("\n" + over + "\n");
                System.out.println("four runs");
                ++balls;
                runs = runs + choice;
                batsmanRunCount(choice);
                tempDisp();
                endOfOver();
                break;
            case 5:
                System.out.println("\n" + over + "\n");
                System.out.println("wide ball");
                ++extra;
                ++runs;
                tempDisp();
                break;
            case 6:
                over = setOvers(over);
                bowler.overBowled = setOvers(bowler.overBowled);
                System.out.println("\n" + over + "\n");
                System.out.println("six runs");
                ++balls;
                runs = runs + choice;
                batsmanRunCount(choice);
                tempDisp();
                endOfOver();
                break;
            case 7:
                System.out.println("\n" + over + "\n");
                System.out.println("no ball");
                ++extra;
                ++runs;
                tempDisp();
                break;
            case 8:
                over = setOvers(over);
                bowler.overBowled = setOvers(bowler.overBowled);
                System.out.println("\n" + over + "\n");
                System.out.println("wicket");
                ++balls;
                batsmanRunCount(0);
                wickets++;
                bowler.wickets++;
                tempDisp();
                newBatsman();
                endOfOver();
                break;
            default:
                break;
        }
    }

    static class Player implements Serializable {
        private static int counterId = 1;
        private String name;
        private String team;
        private int matchesPlayed = 0;
        private int runs;
        private int out;
        private String overBowled = "0.0";
        private int ballsPlayed;
        private int wickets;
        private double strikeRate;
        private boolean onStrike;
        private String type;
        private int uniqueId;
        private boolean outInMatch = false;
        private boolean bowledOver = false;
        private boolean pavillion = true;

        Player(String name, String team, String type) {
            this.name = name;
            this.team = team;
            this.type = type;
            this.uniqueId = counterId++;
        }

        Player() {
        }

        @Override
        public String toString() {
            return "Player{" +
                    "name='" + name + '\'' +
                    ", team='" + team + '\'' +
                    ", matchesPlayed=" + matchesPlayed +
                    ", runs=" + runs +
                    ", out=" + out +
                    ", oversBowled=" + overBowled +
                    ", ballsPlayed=" + ballsPlayed +
                    ", wickets=" + wickets +
                    ", strikeRate=" + strikeRate +
                    ", onStrike=" + onStrike +
                    ", type='" + type + '\'' +
                    ", uniqueId=" + uniqueId +
                    "\n" +
                    '}';
        }
    }

    static class Team implements Serializable {
        private Player[] teamPlayers;
        private int matches = 0;
        private int matchesWon = 0;
        private int matchesLost = 0;
        private int matchesDraw = 0;
        private String teamName;
        private int tossSelection;

        public Team() {
        }

        Team(String teamName) {
            this.teamName = teamName;
            this.playerCreator();
        }

        private void playerCreator() {
            this.teamPlayers = new Player[11];
            Random random = new Random();
            int randomWicketKeeper;
            randomWicketKeeper = random.nextInt(5);
            for (int i = 0; i < teamPlayers.length; i++) {
                if (i < 5 && i == randomWicketKeeper) {
                    this.teamPlayers[i] = new Player("Player-" + (i + 1) + "-Team" + teamName, teamName, "Wicketkeeper-batsman");
                } else if (i < 5 && i != randomWicketKeeper) {
                    this.teamPlayers[i] = new Player("Player-" + (i + 1) + "-Team" + teamName, teamName, "batsman");
                } else if (i > 4 && i < 7) {
                    this.teamPlayers[i] = new Player("Player-" + (i + 1) + "-Team" + teamName, teamName, "allrounder");
                } else {
                    this.teamPlayers[i] = new Player("Player-" + (i + 1) + "-Team" + teamName, teamName, "bowler");
                }
            }
        }

        @Override
        public String toString() {
            return "Team[" +
                    "matches=" + matches +
                    ", matchesWon=" + matchesWon +
                    ", matchesLost=" + matchesLost +
                    ", matchesDraw=" + matchesDraw +
                    ", teamName='" + teamName + '\'' +
                    ",\nteamPlayers=\n" + Arrays.toString(teamPlayers) +
                    "\n" +
                    ']';
        }
    }

}
