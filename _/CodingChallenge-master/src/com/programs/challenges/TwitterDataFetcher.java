package com.programs.challenges;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;
import twitter4j.PagableResponseList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterDataFetcher {

	private static final String CONSUMER_KEY = "v8loaVu021rA6a6r1NOFWza0C";
	private static final String CONSUMER_SECRET = "rlxd7IbEB35zApyH2NPkyShz0KzoDZyPcjRKBz9H3X8K9CaVz0";
	private static final String ACCESS_KEY = "482240289-BngnKI5B0lYYFk3WbrgooWwriSFcVQfOlR3vNMOi";
	private static final String ACCESS_SECRET = "nUuDks7oiUrgQVW3segsiPQFK0GdL6SCdDiyQQtmbWZWm";
	private static Configuration configuration;
	private Twitter twitter;

	/**
	 * Usage: java twitter4j.examples.timeline.GetUserTimeline
	 *
	 * @param args
	 *            String[]
	 * @throws TwitterException
	 */
	public static void main(String[] args) throws TwitterException {
		TwitterDataFetcher tdf = new TwitterDataFetcher();
		//tdf.searchTweetByQuery("Pepperfry");
		//tdf.getUserTimeline("Pepperfry");
		//tdf.getFollowersList("spranshu1");
		try {
			SentenceDetect();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get user timeline
	 * 
	 * @param user
	 *            user string whose timeline to be searched
	 */
	public void getUserTimeline(String user) {
		twitter = getTwitterInstance();
		try {
			List<Status> statuses;
			if (user.equals(" ") || user.isEmpty()) {
				user = twitter.verifyCredentials().getScreenName();
				statuses = twitter.getUserTimeline();
			} else {
				statuses = twitter.getUserTimeline(user);
			}
			System.out.println("Showing @" + user + "'s user timeline.");
			for (Status status : statuses) {
				System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
			}
			System.out.printf("%s\n", "-----------------------------------");
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}

	}

	/**
	 * Retrieves followers of user
	 */
	public void getFollowersList(String user) {
		System.out.println("Getting Followers");
		twitter = getTwitterInstance();
		try {
			PagableResponseList<User> followersList = twitter.getFollowersList(user, -1);
			for (User follower : followersList) {
				System.out.println(follower.getName() + "-" + follower.getId());
			}
			System.out.printf("%s\n", "-----------------------------------");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method search tweets based on provided query
	 * 
	 * @param queryString
	 *            query to be searched
	 */
	public void searchTweetByQuery(String queryString) {
		System.out.println("Query to search.." + queryString);
		twitter = getTwitterInstance();
		Query query = new Query(queryString);
		QueryResult result;
		try {
			result = twitter.search(query);
			System.out.println("Searching tweets...");
			for (Status status : result.getTweets()) {

				System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			}
			System.out.println(System.lineSeparator());
			System.out.printf("%s\n", "-----------------------------------");
		} catch (TwitterException e) {
			System.out.println("Error occured while searching query");
			e.printStackTrace();
		}

	}

	/**
	 * Extract sentence from string
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static void SentenceDetect() throws InvalidFormatException, IOException {
		// always start with a model, a model is learned from training data
		
		try(Scanner sc = new Scanner(System.in);
				InputStream is = new FileInputStream("/usr/local/java/en-sent.bin");) {
			
			System.out.println("Please enter the paragraph -");
			String paragraph = sc.nextLine();
			SentenceModel model = new SentenceModel(is);
			SentenceDetectorME sdetector = new SentenceDetectorME(model);

			String[] sentences = sdetector.sentDetect(paragraph);

			for(String sentence : sentences){
				System.out.println(sentence);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	/**
	 * Returns Twitter instance from TwitterFactory
	 * 
	 * @return Twitter
	 */
	public Twitter getTwitterInstance() {

		return new TwitterFactory(getConfiguratio()).getInstance();

	}

	/**
	 * Set twitter configuration
	 */
	public static void setConfiguration() {
		System.out.println("Setting configuration");
		// gets Twitter instance with defined credentials
		// Twitter Conf.
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_SECRET)
				.setOAuthAccessToken(ACCESS_KEY).setOAuthAccessTokenSecret(ACCESS_SECRET);
		configuration = cb.build();
	}

	public static Configuration getConfiguratio() {
		if (configuration == null) {
			setConfiguration();
			return configuration;
		} else {
			System.out.println("Getting configuration");
			return configuration;
		}
	}

}
