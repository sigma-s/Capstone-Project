package com.example.goodbox.capstone_stage2.rest;

/**
 * Created by Goodbox on 27-01-2017.
 */

public class UserStats {
    private static int score;
    private static int numQuiz;
    private static int numCorrect;
    private static int numIncorrect;
    private static long avgTime;


    public UserStats()
    {
        score = 0;
        numQuiz = 0;
        numCorrect = 0;
        numIncorrect = 0;
        avgTime = 0;
    }

    public static void addScore(int newScore)
    {
        score += newScore;
    }

    public static void incNumQuiz()
    {
        numQuiz++;
    }
    public static void incCorrect(){numCorrect++;}
    public static void incIncorrect(){
        numIncorrect++;}

    public static void addNumCorrect(int correct)
    {
        numCorrect += correct;
    }

    public static void addNumIncorrect(int wrong)
    {
        numIncorrect += wrong;
    }

    public static void calcAvgTime()
    {
        if(numQuiz + numIncorrect == 0)
        {
            avgTime = 0;
        }
        else
        {
            avgTime = ((3* numQuiz)/(numCorrect+ numIncorrect)) * 1000;
        }
    }

    public static void reset()
    {
        score = 0;
        numIncorrect = 0;
        numCorrect = 0;
        numQuiz = 0;
        avgTime = 0;
    }

    public static int getScore()
    {return score;}

    public static int getNumQuiz()
    {return numQuiz;}

    public static int getNumCorrect()
    {return numCorrect;}

    public static int getNumIncorrect()
    {return numIncorrect;}

    public static long getAvgTime()
    {return avgTime;}
}
