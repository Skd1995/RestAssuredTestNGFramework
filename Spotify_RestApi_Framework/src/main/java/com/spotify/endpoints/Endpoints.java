package com.spotify.endpoints;

public class Endpoints {

	public static int OK =200;
	public static long time =3000;
	public static int created = 201;
	
	/********************************ALBUMS*********************************/
	public static String GET_ALBUM="/albums/";
	public static String GET_SEVERAL_ALBUMS="/albums";
	public static String SAVE_ALBUMS="/me/albums";
	public static String GET_SAVED_ALBUMS="/albums";
	public static String GET_ALBUMS="/albums/";
	public static String NEW_RELEASES="/browse/new-releases";
	public static String ALBUM_TRACKS="/albums/{id}/tracks";
	public static String REMOVE_ALBUMS="/me/albums";
	
	/********************************GENRES*********************************/
	public static String GET_AVAILABLE_GENRES="/recommendations/available-genre-seeds";
	
	/********************************CATEGORIES*********************************/
	public static String SEVERAL_BROWSE_CATEGORY="/browse/categories";
	public static String SINGLE_BROWSE_CATEGORY="/browse/categories/";

	/********************************MARKETS*********************************/
	public static String AVAILABLE_MARKET="/markets";
	
	/********************************PLAYER*********************************/
	public static String RECENTLY_PLAYED_TRACK="/me/player/recently-played";
	public static String GET_PLAYBACK_STATE="/me/player";
	public static String AVAILABLE_DEVICES="/me/player/devices";
	public static String CURRENT_PLAYING_TRACK="/me/player/currently-playing";
	
	/********************************USERS*********************************/
	public static String GET_CURRENT_USERS_PROFILE="/me";
	public static String GET_USERS_PROFILE="/users/";
	public static String GET_USERS_TOP_ITEMS="/me/top/tracks";
	public static String GET_FOLLOWED_ARTISTS="/me/following";
	public static String FOLLOW_PLAYLIST="/playlists/{id}/followers";
	
	/********************************ARTIST*********************************/
	public static String GET_ARTIST="/artists/";
	public static String GET_SEVERAL_ARTISTS="/artists";
	public static String GET_ARTISTS_ALBUMS="/artists/{id}/albums";
	public static String GET_ARTISTS_RELATED_ARTISTS="/artists/{id}/related-artists";
	
	/********************************SHOWS*********************************/
	public static String GET_SHOW="/shows/";
	public static String GET_SEVERAL_SHOWS="/shows";
	public static String GET_SHOW_EPISODES="/shows/{id}/episodes";
	public static String SAVE_SHOWS_FOR_CURRENT_USER="/me/shows";
	public static String CHECK_USERS_SAVED_SHOWS="/me/shows/contains";
	public static String GET_USERS_SAVED_SHOWS="/me/shows";
	
	/********************************EPISODES*********************************/
	public static String GET_EPISODE="/episodes/";
	public static String GET_SEVERAL_EPISODES="/episodes";
	public static String REMOVE_USERS_SAVED_EPISODES="/me/episodes";
	public static String SAVE_EPISODES_FOR_CURRENT_USER="/me/episodes";
	public static String CHECK_USERS_SAVED_EPISODES="/me/shows/contains";
	public static String GET_USERS_SAVED_EPISODES="/me/episodes";
	
	
}
