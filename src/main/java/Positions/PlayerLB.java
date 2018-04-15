package Positions;

import java.util.ArrayList;

import Simulation.Team;

/**
 * Created by ahngu on 10/9/2017.
 * <p>
 * Linebacker needs to stop the run and defend TE and most importantly, tackle.
 * <p>
 * Currently using the F7 role as base
 */

public class PlayerLB extends Player {

    //Coverage against TEs
    public int ratCoverage;
    //OLBkR affects how well he defends running plays
    public int ratRunStop;
    //LB Tackle Ability
    public int ratTackle;
    //public int ratSpeed;
    public int ratSpeed;


    //Stats
    public int statsTackles;
    public int statsSacks;
    public int statsFumbles;
    public int statsInts;

    public int careerTackles;
    public int careerSacks;
    public int careerFumbles;
    public int careerInts;

    //Size Config
    private int hAvg = 74;
    private int hMax = 3;
    private int hMin = -5;
    private int wAvg = 246;
    private int wMax = 30;
    private int wMin = -30;

    public PlayerLB(Team t, String nm, int yr, int reg, int trait, int iq, int scout, boolean transfer, boolean wasRS, int pot, int dur, boolean rs, int cov, int rsh, int tkl, int spd, int h, int w) {
        position = "LB";
        team = t;
        name = nm;
        year = yr;

        ratOvr = (cov + 2 * rsh + 2 * tkl + spd) / 6;
        ratPot = pot;
        ratFootIQ = iq;
        ratDur = dur;
        ratCoverage = cov;
        ratRunStop = rsh;
        ratTackle = tkl;
        ratSpeed = spd;
        isRedshirt = rs;
        if (isRedshirt) year = 0;
        wasRedshirt = wasRS;

        region = reg;
        personality = trait;
        recruitRating = scout;
        height = h;
        weight = w;

        resetSeasonStats();
        resetCareerStats();
    }

    public PlayerLB(Team t, String nm, int yr, int reg, int trait, int iq, int scout, boolean transfer, boolean wasRS, boolean wo, int pot, int dur, boolean rs, int cGamesPlayed, int cWins, int cHeismans, int cAA, int cAC, int cTF, int cAF,
                    int cov, int rsh, int tkl, int spd, int h, int w, int cTackles, int cSacks, int cFumbles, int cInts) {
        position = "LB";
        team = t;
        name = nm;
        year = yr;

        ratOvr = (cov + rsh * 2 + tkl * 2) / 5;
        ratPot = pot;
        ratFootIQ = iq;
        ratDur = dur;
        ratCoverage = cov;
        ratRunStop = rsh;
        ratTackle = tkl;
        ratSpeed = spd;
        isRedshirt = rs;
        if (isRedshirt) year = 0;
        wasRedshirt = wasRS;

        isTransfer = transfer;
        isWalkOn = wo;
        region = reg;
        personality = trait;
        troubledTimes = 0;
        recruitRating = scout;
        height = h;
        weight = w;

        resetSeasonStats();

        careerGames = cGamesPlayed;
        careerHeismans = cHeismans;
        careerAllAmerican = cAA;
        careerAllConference = cAC;
        careerTopFreshman = cTF;
        careerAllFreshman = cAF;
        careerWins = cWins;

        careerTackles = cTackles;
        careerSacks = cSacks;
        careerFumbles = cFumbles;
        careerInts = cInts;

    }

     public PlayerLB(String nm, int yr, int stars, Team t) {
         position = "LB";
         height = hAvg + (int) (Math.random() * ((hMax - hMin) + 1)) + hMin;
         weight = wAvg + (int) (Math.random() * ((wMax - wMin) + 1)) + wMin;
         name = nm;
         year = yr;
         team = t;

        ratPot = (int) (attrBase + 50 * Math.random());
        ratFootIQ = (int) (attrBase + 50 * Math.random());
        ratDur = (int) (attrBase + 50 * Math.random());
        ratCoverage = (int) (ratBase + year*yearFactor + stars*starFactor - ratTolerance*Math.random());
        ratRunStop = (int) (ratBase + year*yearFactor + stars*starFactor - ratTolerance*Math.random());
        ratTackle = (int) (ratBase + year*yearFactor + stars*starFactor - ratTolerance*Math.random());
        ratSpeed = (int) ((ratBase-15) + year*yearFactor + stars*starFactor - ratTolerance*Math.random());
        ratOvr = (ratCoverage + ratRunStop * 2 + ratTackle * 2 + ratSpeed) / 6;
        region = (int)(Math.random()*5);
        personality = (int) (attrBase + 50 * Math.random());

         recruitRating = getScoutingGrade();

         recruitTolerance = (int)((60 - team.teamPrestige)/lbImportance);
         cost = (int)((Math.pow((float) ratOvr - costBaseRating, 2)/5) + (int)Math.random()*recruitTolerance);

         cost = (int)(cost/lbImportance);

         double locFactor = Math.abs(team.location - region) - 2.5;
         cost = cost + (int)(Math.random()*(locFactor * locationDiscount));
         if (cost < 0) cost = (int)Math.random()*7+1;

         resetSeasonStats();
         resetCareerStats();


    }

    public PlayerLB(String nm, int yr, int stars, Team t, boolean custom) {
        name = nm;
        year = yr;
        team = t;

        ratPot = (int) (attrBase + 50 * Math.random());
        ratFootIQ = (int) (attrBase + 50 * Math.random());
        ratDur = (int) (attrBase + 50 * Math.random());
        ratCoverage = (int) (ratBase + stars * customFactor - ratTolerance * Math.random());
        ratRunStop = (int) (ratBase + stars * customFactor - ratTolerance * Math.random());
        ratTackle = (int) (ratBase + stars * customFactor - ratTolerance * Math.random());
        ratSpeed = (int) ((ratBase-15) + stars * customFactor - ratTolerance * Math.random());
        ratOvr = (ratCoverage + ratRunStop * 2 + ratTackle * 2 + ratSpeed) / 6;
        position = "LB";
        region = (int)(Math.random()*5);
        personality = (int) (attrBase + 50 * Math.random());

        if(custom) isWalkOn = true;
        recruitRating = getScoutingGrade();

        resetSeasonStats();
        resetCareerStats();

        height = hAvg + 	(int)(Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + 	(int)(Math.random() * ((wMax - wMin) + 1)) + wMin;
    }

    @Override
    public void advanceSeason() {
        int oldOvr = ratOvr;
        progression = (ratPot * 3 + team.HC.get(0).ratTalent * 2 + team.HC.get(0).ratDef) / 6;
        int games = gamesStarted + (gamesPlayed-gamesStarted)/3;

        if (!isMedicalRS) {
            year++;
            if (wonAllConference) ratPot += (int)Math.random()*allConfPotBonus;
            if (wonAllAmerican) ratPot += (int)Math.random()*allAmericanBonus;
            if (wonAllFreshman) ratPot += (int)Math.random()*allFreshmanBonus;
            if (wonTopFreshman) ratPot += (int)Math.random()*topBonus;
            if (wonHeisman) ratPot += (int)Math.random()*topBonus;

            if (year > 2 && games < minGamesPot) ratPot -= (int) (Math.random() * 15);

            ratFootIQ += (int) (Math.random() * (progression + games - 35)) / 10;
            ratCoverage += (int) (Math.random() * (progression + games - 35)) / 10;
            ratRunStop += (int) (Math.random() * (progression + games - 35)) / 10;
            ratTackle += (int) (Math.random() * (progression + games - 35)) / 10;
            ratSpeed += (int) (Math.random() * (progression + games - 35)) / 10;
            if (Math.random() * 100 < progression) {
                //breakthrough
                ratCoverage += (int) (Math.random() * (progression + games - 40)) / 10;
                ratRunStop += (int) (Math.random() * (progression + games - 40)) / 10;
                ratTackle += (int) (Math.random() * (progression + games - 40)) / 10;
                ratSpeed += (int) (Math.random() * (progression + games - 40)) / 10;
            }
        }
        
        ratOvr = (ratCoverage + ratRunStop * 2 + ratTackle * 2 + ratSpeed) / 6;
        ratImprovement = ratOvr - oldOvr;

        careerGames += gamesPlayed;
        careerWins += statsWins;

        careerTackles += statsTackles;
        careerSacks += statsSacks;
        careerFumbles += statsFumbles;
        careerInts += statsInts;

        resetSeasonStats();

        if (wonHeisman) careerHeismans++;
        if (wonAllAmerican) careerAllAmerican++;
        if (wonAllConference) careerAllConference++;
        if (wonAllFreshman) careerAllFreshman++;
        if (wonTopFreshman) careerTopFreshman++;

        if (isTransfer) {
            isTransfer = false;
            year -= 1;
        }

        if (isRedshirt) wasRedshirt = true;

    }

    private void resetSeasonStats() {
        gamesStarted = 0;
        gamesPlayed = 0;
        isInjured = false;
        troubledTimes = 0;

        wonHeisman = false;
        wonAllAmerican = false;
        wonAllConference = false;
        wonAllFreshman = false;
        wonTopFreshman = false;
        statsWins = 0;
        statsTackles = 0;
        statsSacks = 0;
        statsFumbles = 0;
        statsInts = 0;
    }

    private void resetCareerStats() {
        careerGames = 0;
        careerHeismans = 0;
        careerAllAmerican = 0;
        careerAllConference = 0;
        careerAllFreshman = 0;
        careerTopFreshman = 0;
        careerWins = 0;

        careerTackles = 0;
        careerSacks = 0;
        careerFumbles = 0;
        careerInts = 0;
    }

    @Override
    public int getHeismanScore() {
        return statsTackles*25 + statsSacks*425 + statsFumbles*425 + statsInts*425 + ratOvr*10 + team.teamPrestige*3 + team.confPrestige*3 + (120 - team.rankTeamPollScore)*2;
    }

    @Override
    public int getCareerScore() {
        return statsTackles*25 + statsSacks*425 + statsFumbles*425 + statsInts*425 + ratOvr*10 + team.teamPrestige*4 +
                careerTackles*25 + careerSacks*425 + careerFumbles*425 + careerInts*425 + ratOvr*year*10;    }

    @Override
    public ArrayList<String> getDetailStatsList(int games) {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("Height " + getHeight() + ">Weight: " + getWeight());
        pStats.add("Tackles: " + (statsTackles) + " >Sacks: " + (statsSacks));
        pStats.add("Fumbles: " + (statsFumbles) + " >Interceptions: " + (statsInts));
        pStats.add("Games: " + gamesPlayed + " (" + statsWins + "-" + (gamesStarted - statsWins) + ")" + "> ");
        pStats.add("Coverage: " + getLetterGrade(ratCoverage) + ">Run Stop: " + getLetterGrade(ratRunStop));
        pStats.add("Tackling: " + getLetterGrade(ratTackle) + ">Speed: " + getLetterGrade(ratSpeed));
        pStats.add("Durability: " + getLetterGrade(ratDur) + ">Football IQ: " + getLetterGrade(ratFootIQ));
        pStats.add("Home Region: " + getRegion(region) + ">Personality: " + getLetterGrade(personality));
        pStats.add("Scout Grade: " + getScoutingGradeString() + " > " + getStatus());
        pStats.add(" > ");
        return pStats;
    }

    @Override
    public ArrayList<String> getDetailAllStatsList(int games) {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("Height " + getHeight() + ">Weight: " + getWeight());
        pStats.add("Tackles: " + (statsTackles) + " >Sacks: " + (statsSacks));
        pStats.add("Fumbles: " + (statsFumbles) + " >Interceptions: " + (statsInts));
        pStats.add("Games: " + gamesPlayed + " (" + statsWins + "-" + (gamesStarted - statsWins) + ")" + "> ");
        pStats.add("Coverage: " + getLetterGrade(ratCoverage) + ">Run Stop: " + getLetterGrade(ratRunStop));
        pStats.add("Tackling: " + getLetterGrade(ratTackle) + ">Speed: " + getLetterGrade(ratSpeed));
        pStats.add("Durability: " + getLetterGrade(ratDur) + ">Football IQ: " + getLetterGrade(ratFootIQ));
        pStats.add("Home Region: " + getRegion(region) + ">Personality: " + getLetterGrade(personality));
        pStats.add("Scout Grade: " + getScoutingGradeString() + " > " + getStatus());
        pStats.add(" > ");
        pStats.add("[B]CAREER STATS:");
        pStats.addAll(getCareerStatsList());
        return pStats;
    }

    @Override
    public ArrayList<String> getCareerStatsList() {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("Tackles: " + (statsTackles + careerTackles) + " >Sacks: " + (statsSacks + careerSacks));
        pStats.add("Fumbles: " + (statsFumbles + careerFumbles) + " >Interceptions: " + (statsInts + careerInts));
        pStats.addAll(super.getCareerStatsList());
        return pStats;
    }


    @Override
    public String getInfoForLineup() {
        if (injury != null)
            return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(ratPot, ratOvr, year, team.HC.get(0).ratTalent) + " " + injury.toString();
        return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(ratPot, ratOvr, year, team.HC.get(0).ratTalent) + " (" +
                getLetterGrade(ratCoverage) + ", " + getLetterGrade(ratRunStop) + ", " + getLetterGrade(ratTackle) + ", " + getLetterGrade(ratSpeed) + ")";
    }

}
