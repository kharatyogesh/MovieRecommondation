package org.movie.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.movie.model.GenresMasterModel;
import org.movie.model.MovieMasterModel;
import org.movie.model.MovieRating;
import org.movie.model.UserMasterModel;
import org.movie.repository.GenresRepository;
import org.movie.repository.MovieRepository;
import org.movie.repository.RatingRepository;
import org.movie.service.AdminService;
import org.movie.service.GenresService;
import org.movie.service.MovieService;
import org.movie.service.NaiveBayesService;
import org.movie.service.UserService;

public class MoviePredictionApp {

	public static void main(String[] args) {

		AdminService ads = new AdminService();
		MovieService ms = new MovieService();
		GenresService gs = new GenresService();
		UserService us = new UserService();

		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("==================WELCOME TO THE MOVIE RECOMMENDAION SYSTEM===============");
			System.out.println("1 for Admin Login");
			System.out.println("2 for User Login");

			System.out.println("Enter your choice");
			int choice = sc.nextInt();

			switch (choice) {

			// Admin Login start
			case 1:
				sc.nextLine();
				System.out.println("============ADMIN LOGIN=============");
				System.out.println("Enter the username");
				String id = sc.nextLine();

				System.out.println("Enter the password");
				String pass = sc.nextLine();

				boolean b = ads.isAdminValid(id, pass);

				if (b) {

					System.out.println("Admin Login Successfull...........");

					do {
						System.out.println("======================ADMIN MENU===========================");
						System.out.println("Enter 1 for Add New Movie");
						System.out.println("Enter 2 for Display All Movies");
						System.out.println("Enter 3 for Search Movie");
						System.out.println("Enter 4 for update Movie");
						System.out.println("Enter 5 for delete Movie");
						System.out.println("Enter 6 for Add New Genre");
						System.out.println("Enter 7 for display All Genres");
						System.out.println("Enter 8 for search Genres ");
						System.out.println("Enter 9 for update Genres ");
						System.out.println("Enter 10 for delete Genres");
						System.out.println("Enter 11 for EXIT from Admin...........");
						System.out.println("======================ADMIN MENU===========================");

						System.out.println("Enter the choice");
						choice = sc.nextInt();

						switch (choice) {
						/*
						 * ==================================CASE 1-Add
						 * MOVIE========================================
						 */
						case 1:
							sc.nextLine();

							List<GenresMasterModel> list3 = gs.getAllGenres();

							System.out.println("=====================Genres List======================");
							if (list3 != null) {
								System.out.printf("%-10s | %-20s%n", "Genre Id", "Genre Name");
								System.out.println("------------------------------------------");
								list3.forEach(
										(m) -> System.out.printf("%-10d | %-20s%n", m.getGenId(), m.getGenName()));
								System.out.println("------------------------------------------");

							} else {
								System.out.println("There is no genres present.........");
							}
							System.out.println("=====================Genres List======================");
							System.out.println("Enter the movie genre ");
							String mgenre = sc.nextLine();

							int genId = gs.getGenreId(mgenre);

							if (genId != -1) {

								System.out.println("Enter the movie name");
								String mname = sc.nextLine();
								System.out.println("Enter the movie release data (YYYY-MM-DD)");
								String mdate = sc.nextLine();

								int movid = ms.getMovieIdAutomatic();
								MovieMasterModel model = new MovieMasterModel();
								model.setGenId(genId);
								model.setMovTitle(mname);
								model.setMovDate(mdate);
								model.setMovieId(movid);
								b = ms.addNewMovie(model);

								if (b) {
									System.out.println("movie Added successfully........");
								} else {
									System.out.println("Some problem in added movie");
								}

							} else {
								System.out.println("No genre Present........");
							}

							break;

						/*
						 * ==================================CASE 2-Display
						 * Movies========================================
						 */
						case 2:

							List<MovieMasterModel> list = ms.getAllMovie();

							if (list != null) {
								System.out.printf("%-10s | %-20s | %-15s | %-20s%n", "Movie Id", "Movie Name",
										"Release Date", "Genre Name");
								System.out.println("------------------------------------------------------------");
								list.forEach((m) -> {
									System.out.printf("%-10d | %-20s | %-15s | %-20s%n", m.getMovieId(),
											m.getMovTitle(), m.getMovDate(), m.getGenName());
									System.out.println("------------------------------------------------------------");
								});
							} else {
								System.out.println("There are no movies present.");
							}
							break;

//							List<MovieMasterModel> list = ms.getAllMovie();
//
//							if (list != null) {
//								list.forEach((m) ->
//								System.out.println(m.getMovieId() + "\t" + m.getMovTitle() + "\t" + m.getMovDate()+"\t"+m.getGenName()));
//							} else {
//								System.out.println("There is no Movie present.........");
//							}
//							break;
//						
						/*
						 * ==================================CASE
						 * 3-SEARCH========================================
						 */
						case 3:
							do {
								System.out.println("=============Search Movie Menu============");
								// System.out.println("Enter 1:Search Movie By Id");
								System.out.println("Enter 1:Search Movie By Name");
								System.out.println("Enter 2:Are you want to exit from search menu");
								System.out.println("=============Search Movie Menu============");
								System.out.println("Enter your choice");
								choice = sc.nextInt();

								switch (choice) {

//								case 1:
//
//									sc.nextLine();
//									System.out.println("Enter Id of Movie for search");
//									int mid = sc.nextInt();
//
//									list = ms.searchMovieById(mid);
//
//									if (list != null) {
//										System.out.printf("%-10s | %-20s | %-15s | %-20s%n", "Movie Id", "Movie Name",
//												"Release Date", "Genre Name");
//										System.out.println(
//												"------------------------------------------------------------");
//										list.forEach((m) -> {
//											System.out.printf("%-10d | %-20s | %-15s | %-20s%n", m.getMovieId(),
//													m.getMovTitle(), m.getMovDate(), m.getGenName());
//											System.out.println(
//													"------------------------------------------------------------");
//										});
//									} else {
//										System.out.println("There is No movie  present.........");
//									}
//
//									break;
								case 1:

									sc.nextLine();
									System.out.println("Enter Name of Movie for search");
									String movieName = sc.nextLine();

									List<MovieMasterModel> list1 = ms.searchMovieByName(movieName);

									if (list1 != null) {
										System.out.printf("%-10s | %-20s | %-15s | %-20s%n", "Movie Id", "Movie Name",
												"Release Date", "Genre Name");
										System.out.println(
												"------------------------------------------------------------");
										list1.forEach((m) -> {
											System.out.printf("%-10d | %-20s | %-15s | %-20s%n", m.getMovieId(),
													m.getMovTitle(), m.getMovDate(), m.getGenName());
											System.out.println(
													"------------------------------------------------------------");
										});
									} else {
										System.out.println("There is No movie  present.........");
									}

									break;

								case 2:
									System.out.println("Exit from Search .........");
									System.out.println("==============================");
									break;

								default:
									System.out.println("Please enter a valid choice..........");
									break;

								}
							} while (choice != 2);

							break;

						/*
						 * ==================================CASE
						 * 4-UPDATE========================================
						 */
						case 4:
							sc.nextLine();
							do {
								System.out.println("=============Update Movie Menu============");
								System.out.println("Enter 1: Update Movie information By Id");
								System.out.println("Enter 2: Update Movie Information By Name");
								System.out.println("Enter 3: Exit From Update Menu");
								System.out.println("=============Update Movie Menu============");
								System.out.println("Enter Your Choice");
								choice = sc.nextInt();

								switch (choice) {
								case 1:
									sc.nextLine();
									System.out.println("Enter Id of Movie for update information");
									int upid = sc.nextInt();

									sc.nextLine();
									if (ms.movieExistsById(upid)) {
										System.out.println("Enter the new Title of the movie");
										String newTitle = sc.nextLine();
										System.out.println("Enter the new release date of the movie");
										String newDate = sc.nextLine();

										System.out.println("Enter the new genre Id of the movie");
										int newGenreID = sc.nextInt();

										b = ms.updateMovieById(upid, newTitle, newDate, newGenreID);
										if (b) {
											System.out.println("Movie Updated succesfully............");
										} else {
											System.out.println("Movie Updation error........");
										}
									} else {
										System.out.println("Movie with ID " + upid + " does not exist.");

									}
									break;

								case 2:
									sc.nextLine();
									System.out.println("Enter Name of Movie for update information");
									String upName = sc.nextLine();

									if (ms.movieExistsByName(upName)) {
										System.out.println("Enter the new Title of the movie");
										String newTitle = sc.nextLine();
										System.out.println("Enter the new release date of the movie");
										String newDate = sc.nextLine();

										b = ms.updateMovieByName(upName, newTitle, newDate);
										if (b) {
											System.out.println("Movie Updated succesfully............");
										} else {
											System.out.println("Movie Updation error........");
										}
									} else {
										System.out.println("Movie with name \"" + upName + "\" does not exist.");

									}
									break;

								case 3:
									System.out.println("Exit From Update...............");
									System.out.println("==============================");
									break;

								default:
									System.out.println("Please enter a valid choice..........");
									break;

								}
							} while (choice != 3);

							break;

						/*
						 * ==================================CASE
						 * 5-DELETE========================================
						 */
						case 5:
							sc.nextLine();
							do {

								System.out.println("=============Delete Movie Menu============");
								System.out.println("Enter 1: Delele Movie information By Id");
								System.out.println("Enter 2: Delete Movie Information By Name");
								System.out.println("Enter 3: Exit From Delete Menu");
								System.out.println("=============Delete Movie Menu============");

								System.out.println("Enter Your Choice");
								choice = sc.nextInt();

								switch (choice) {
								case 1:
									sc.nextLine();
									System.out.println("Enter Id of Movie for Delete information");
									int upid = sc.nextInt();

									sc.nextLine();
									if (ms.movieExistsById(upid)) {

										b = ms.deleteMovieById(upid);
										if (b) {
											System.out.println("Movie Deleted succesfully............");
										} else {
											System.out.println("Movie Deleted error........");
										}
									} else {
										System.out.println("Movie with ID " + upid + " does not exist.");

									}
									break;

								case 2:
									sc.nextLine();
									System.out.println("Enter Name of Movie for delete information");
									String upName = sc.nextLine();

									if (ms.movieExistsByName(upName)) {

										b = ms.deleteMovieByName(upName);
										if (b) {
											System.out.println("Movie Updated succesfully............");
										} else {
											System.out.println("Movie Updation error........");
										}
									} else {
										System.out.println("Movie with name \"" + upName + "\" does not exist.");

									}
									break;

								case 3:
									System.out.println("Exit From Delete...............");
									System.out.println("==============================");
									break;

								default:
									System.out.println("Please enter a valid choice..........");
									break;

								}
							} while (choice != 3);

							break;
						/*
						 * ==================================CASE 6-GENRE
						 * ADD========================================
						 */
						case 6:

							sc.nextLine();
							System.out.println("Enter Genre Name");
							String gname = sc.nextLine();

							GenresMasterModel gmodel = new GenresMasterModel();
							gmodel.setGenName(gname);

							b = gs.isAddGenre(gmodel);
							if (b) {
								System.out.println(" Genre Added Successfully............");
							} else {
								System.out.println("Some problem in added...........");
							}

//							b = gs.addBulkGenres();
//							if (b) {
//								System.out.println("Bulk Genres Added Successfully............");
//							} else {
//								System.out.println("Some problem in added...........");
//							}

							break;
						/*
						 * ==================================CASE 7-DISPLAY
						 * GENRE========================================
						 */
						case 7:

							list3 = gs.getAllGenres();

							System.out.println("=====================Genres List======================");
							if (list3 != null) {
								System.out.printf("%-10s | %-20s%n", "Genre Id", "Genre Name");
								System.out.println("------------------------------------------");
								list3.forEach(
										(m) -> System.out.printf("%-10d | %-20s%n", m.getGenId(), m.getGenName()));
								System.out.println("------------------------------------------");

							} else {
								System.out.println("There is no genres present.........");
							}
							System.out.println("=====================Genres List======================");
							break;

						/*
						 * ==================================CASE 8-SEARCH
						 * GENRE========================================
						 */
						case 8:

							do {
								System.out.println("=============Search Genres Menu============");
								System.out.println("Enter 1:Search Genre By Id");
								System.out.println("Enter 2:Search Genre By Name");
								System.out.println("Enter 3:Are you want to exit from Genre menu");
								System.out.println("=============Search Genres Menu============");
								System.out.println("Enter your choice");
								choice = sc.nextInt();

								switch (choice) {

								case 1:

									sc.nextLine();
									System.out.println("Enter Id of Genre for search");
									int gid = sc.nextInt();

									list3 = gs.searchGenreById(gid);

									if (list3 != null && !list3.isEmpty()) {
										list3.forEach((m) -> System.out.println(m.getGenId() + "\t" + m.getGenName()));
									} else {
										System.out.println("There is No Genre  present.........");
									}

									break;
								case 2:

									sc.nextLine();
									System.out.println("Enter Name of Genre for search");
									String genreName = sc.nextLine();

									list3 = gs.searchGenreByName(genreName);

									if (list3 != null) {
										list3.forEach((m) -> System.out.println(m.getGenId() + "\t" + m.getGenName()));
									} else {
										System.out.println("There is No Genre  present.........");
									}

									break;

								case 3:
									System.out.println("Exit from Search .........");
									System.out.println("==============================");

								default:
									System.out.println("Please enter a valid choice..........");
									break;

								}
							} while (choice != 3);

							break;
						/*
						 * ==================================CASE 9-UPDATE
						 * GENRE========================================
						 */
						case 9:

							sc.nextLine();
							do {
								System.out.println("=============Update Genre Menu============");
								System.out.println("Enter 1: Update Genre information By Id");
								System.out.println("Enter 2: Update Genre Information By Name");
								System.out.println("Enter 3: Exit From Update Menu");
								System.out.println("=============Update Genre Menu============");
								System.out.println("Enter Your Choice");
								choice = sc.nextInt();

								switch (choice) {
								case 1:
									sc.nextLine();
									System.out.println("Enter Id of Genre for update information");
									int genid = sc.nextInt();

									sc.nextLine();
									if (gs.genreExistsById(genid)) {
										System.out.println("Enter the new Name of the Genre");
										String genName = sc.nextLine();

										b = gs.updateGenreById(genid, genName);
										if (b) {
											System.out.println("Genre Updated succesfully............");
										} else {
											System.out.println("Genre Updation error........");
										}
									} else {
										System.out.println("Movie with ID " + genid + " does not exist.");

									}
									break;

								case 2:
									sc.nextLine();
									System.out.println("Enter Name of Genre for update information");
									String upName = sc.nextLine();

									if (gs.genreExistsByName(upName)) {
										System.out.println("Enter the new Title of the genre");
										String genName = sc.nextLine();

										b = gs.updateGenreByName(upName, genName);
										if (b) {
											System.out.println("Genre Updated succesfully............");
										} else {
											System.out.println("Genre Updation error........");
										}
									} else {
										System.out.println("Genre with name \"" + upName + "\" does not exist.");

									}
									break;

								case 3:
									System.out.println("Exit From Update...............");
									System.out.println("==============================");
									break;

								default:
									System.out.println("Please enter a valid choice..........");
									break;

								}
							} while (choice != 3);

							break;
						/*
						 * ==================================CASE 10-DELETE
						 * GENRE========================================
						 */
						case 10:

							sc.nextLine();
							do {

								System.out.println("=============Delete genre Menu============");
								System.out.println("Enter 1: Delele Genre information By Id");
								System.out.println("Enter 2: Delete Genre Information By Name");
								System.out.println("Enter 3: Exit From Delete Menu");
								System.out.println("=============Delete genre Menu============");

								System.out.println("Enter Your Choice");
								choice = sc.nextInt();

								switch (choice) {
								case 1:
									sc.nextLine();
									System.out.println("Enter Id of Genre for Delete information");
									int upid = sc.nextInt();

									sc.nextLine();
									if (gs.genreExistsById(upid)) {

										b = gs.deleteGenreById(upid);
										if (b) {
											System.out.println("Genre Deleted succesfully............");
										} else {
											System.out.println("Genre Deleted error........");
										}
									} else {
										System.out.println("Genre with ID " + upid + " does not exist.");

									}
									break;

								case 2:
									sc.nextLine();
									System.out.println("Enter Name of Genre for delete information");
									String upName = sc.nextLine();

									if (gs.genreExistsByName(upName)) {

										b = gs.deleteGenreByName(upName);
										if (b) {
											System.out.println("Genre Updated succesfully............");
										} else {
											System.out.println("Genre Updation error...........");
										}
									} else {
										System.out.println("Genre with name \"" + upName + "\" does not exist.");

									}
									break;

								case 3:
									System.out.println("Exit From Delete...............");
									System.out.println("==============================");
									break;

								default:
									System.out.println("Please enter a valid choice..........");
									break;

								}
							} while (choice != 3);
							break;

						case 11:

							System.out.println("LOGOUT FROM ADMIN");
							break;

						default:
							System.out.println("Please enter a valid choice..........");
							break;

						}
					} while (choice != 11);
				} else {

					System.out.println("Please enter a valid username or password............");
					System.out.println(" Do you Forgot Password and want to change it");
					String msg = sc.nextLine();
					if (msg.equals("yes")) {
						System.out.println("Enter the username to change the password ");
						String user = sc.nextLine();

						if (ads.checkTheUsername(user)) {
							System.out.println("Enter the new password for the admin login");
							String newPass = sc.nextLine();

							b = ads.changePass(user, newPass);
							if (b) {
								System.out.println("Password Chnages Successfully..........");
							} else {
								System.out.println("Some error in changing password....");
							}

						} else {
							System.out.println("Please Enter a valid Username............");
						}

					} else {
						System.out.println("Thank you!........");
					}
				}

				break;

			// Admin Login End...........

			case 2:
				// User Login Start
				sc.nextLine();
				System.out.println("============USER LOGIN=============");
				do {
					System.out.println("Enter 1 for New User Registration");
					System.out.println("Enter 2 for Existing User Login");
					System.out.println("Enter 3 for Exit From User Login");

					System.out.println("Enter Your Choice");
					choice = sc.nextInt();

					MovieRepository movieRepo;
					RatingRepository ratingRepo;
					GenresRepository genresRepo;
					switch (choice) {

					case 1:
						sc.nextLine();
						System.out.println("Enter the contact Number");
						String con = sc.nextLine();

						System.out.println("Enter the  password ");
						String password = sc.nextLine();

						System.out.println("Enter the name of the user");
						String name = sc.nextLine();

						System.out.println("Enter the city of the user");
						String city = sc.nextLine();

						UserMasterModel userModel = new UserMasterModel();

						userModel.setUserid(con);
						userModel.setPassword(password);
						userModel.setName(name);
						userModel.setCity(city);

						b = us.isAddUser(userModel);
						if (b) {
							System.out.println("User Added SUccessfully..............");
						} else {
							System.out.println("Some problem to add user..........");
						}

						break;

					case 2:

						System.out.println("================Welcome User Login=====================");
						sc.nextLine();
						System.out.println("Enter the contact number ");
						con = sc.nextLine();

						System.out.println("Enter the password");
						String userpass = sc.nextLine();

						b = us.isUserValid(con, userpass);

						if (b) {
							System.out.println("User Login Successfull.................");

							System.out.println("==================Latest 3 movies==================");

							List<MovieMasterModel> list = ms.getLatestThreeMovies();

							if (list != null) {
								System.out.printf("%-10s | %-20s | %-15s | %-20s%n", "Movie Id", "Movie Name",
										"Release Date", "Genre Name");
								System.out.println("------------------------------------------------------------");
								list.forEach((m) -> {
									System.out.printf("%-10d | %-20s | %-15s | %-20s%n", m.getMovieId(),
											m.getMovTitle(), m.getMovDate(), m.getGenName());
									System.out.println("------------------------------------------------------------");
								});
							} else {
								System.out.println("There are no movies present.");
							}

							do {
								System.out.println(
										"====================================USER LOGIN MENU=================================");
								System.out.println("Enter 1 for Watch Movie");
								System.out.println("Enter 2 for Search Movies");
								System.out.println("Enter 3 for Recommond movie to user by genre");
								System.out.println("Enter 4 for Exit");

								System.out.println("Enter the choice");
								choice = sc.nextInt();

								switch (choice) {
								case 1:

									System.out.println("Enter the movie id for watch the movie");
									int mid = sc.nextInt();

									list = ms.searchMovieById(mid);

									if (list != null && !list.isEmpty()) {
										int userId = us.getUserIdByContact(con);

										boolean assigned = us.assignMovieToUser(userId, mid);

										if (assigned) {

											System.out.println("Thank you for watching movie!..........");

											int umId = us.getMovieUserJoinId(userId, mid);

											System.out.println("Please enter your rating for the movie (0 to 5):");
											float rating = sc.nextFloat();

											sc.nextLine();

											System.out
													.println("Enter the comment on movie (good,bad,very good,average)");
											String text = sc.nextLine();

											boolean ratingAdded = us.addRating(umId, rating, text);

											if (ratingAdded) {
												System.out.println("Thank you! Your rating has been recorded.");
											} else {
												System.out.println("Error recording your rating.");
											}

										} else {
											System.out.println("Error to watch movie to user.");
										}
									} else {
										System.out.println("No movie present.........");

									}

									break;

								case 2:

									do {
										System.out.println(
												"=============================SEARCH MENU===========================");
										System.out.println("Enter 1 for search movies genre wise");
										System.out.println("Enter 2 for search movie rating wise");
										System.out.println("Enter 3 for see your watch history");				
										System.out.println("Enter 4 for EXIT...........");

										System.out.println("Enter your choice");
										choice = sc.nextInt();

										switch (choice) {
										case 1:
											sc.nextLine();
											List<GenresMasterModel> list3 = gs.getAllGenres();

											System.out
													.println("=====================Genres List======================");
											if (list3 != null) {
												System.out.printf("%-10s | %-20s%n", "Genre Id", "Genre Name");
												System.out.println("------------------------------------------");
												list3.forEach((m) -> System.out.printf("%-10d | %-20s%n", m.getGenId(),
														m.getGenName()));
												System.out.println("------------------------------------------");

											} else {
												System.out.println("There is no genres present.........");
											}
											System.out
													.println("=====================Genres List======================");
											System.out.println("Enter the Genre id for all movies");
											int genId = sc.nextInt();

											if (genId != -1) {
												List<MovieMasterModel> genreMovies = us.getMoviesByGenreId(genId);
												if (genreMovies != null && !genreMovies.isEmpty()) {
													System.out.printf("%-10s | %-20s | %-15s | %-20s%n", "Movie Id",
															"Movie Name", "Release Date", "Genre Name");
													System.out.println(
															"------------------------------------------------------------");
													genreMovies.forEach((m) -> {
														System.out.printf("%-10d | %-20s | %-15s | %-20s%n",
																m.getMovieId(), m.getMovTitle(), m.getMovDate(),
																m.getGenName());
														System.out.println(
																"------------------------------------------------------------");
													});

													do {
														System.out.println(
																"=====================================================================");
														System.out.println("Enter 1 for Watch Movie");
														System.out.println("Enter 2 for Search Movies by genres");
														System.out.println("Enter 3 for Exit");

														System.out.println("Enter the choice");
														choice = sc.nextInt();

														switch (choice) {

														case 1:
														  int userId = us.getUserIdByContact(con);

														        System.out.println("Enter the movie id to watch the movie:");
														        int mid1 = sc.nextInt();
														        sc.nextLine(); // Consume newline after nextInt()

														        	list = ms.searchMovieById(mid1);

														        if (list != null && !list.isEmpty()) {
														            boolean assigned = us.assignMovieToUser(userId, mid1);

														            if (assigned) {
														                System.out.println("Thank you for watching the movie!");

														                int umId = us.getMovieUserJoinId(userId, mid1);

														                // Check if the user has already rated this movie
														                if (us.hasUserRatedMovie(umId)) {
														                    System.out.println("Please enter your new rating for the movie (0 to 5):");
														                } else {
														                    System.out.println("Please enter your rating for the movie (0 to 5):");
														                }

														                float rating = sc.nextFloat();
														                sc.nextLine(); // Consume newline after nextFloat()

														                System.out.println("Enter your comment on the movie (good, bad, very good, average):");
														                String text = sc.nextLine();

														                boolean ratingAdded = us.addOrUpdateRating(umId, rating, text);

														                if (ratingAdded) {
														                    System.out.println("Thank you! Your rating has been recorded.");
														                } else {
														                    System.out.println("Error recording your rating.");
														                }
														            } else {
														                System.out.println("Error assigning movie to user.");
														            }
														        } else {
														            System.out.println("No movie found with the given id.");
														        }
															
											                    break;
														case 2:
															sc.nextLine();
															list3 = gs.getAllGenres();

															System.out.println(
																	"=====================Genres List======================");
															if (list3 != null) {
																System.out.printf("%-10s | %-20s%n", "Genre Id",
																		"Genre Name");
																System.out.println(
																		"------------------------------------------");
																list3.forEach(
																		(m) -> System.out.printf("%-10d | %-20s%n",
																				m.getGenId(), m.getGenName()));
																System.out.println(
																		"------------------------------------------");

															} else {
																System.out
																		.println("There is no genres present.........");
															}
															System.out.println(
																	"=====================Genres List======================");
															System.out.println("Enter the Genre id for all movies");
															genId = sc.nextInt();

															if (genId != -1) {
																genreMovies = us.getMoviesByGenreId(genId);
																if (genreMovies != null && !genreMovies.isEmpty()) {
																	System.out.printf("%-10s | %-20s | %-15s | %-20s%n",
																			"Movie Id", "Movie Name", "Release Date",
																			"Genre Name");
																	System.out.println(
																			"------------------------------------------------------------");
																	genreMovies.forEach((m) -> {
																		System.out.printf(
																				"%-10d | %-20s | %-15s | %-20s%n",
																				m.getMovieId(), m.getMovTitle(),
																				m.getMovDate(), m.getGenName());
																		System.out.println(
																				"------------------------------------------------------------");
																	});
																} else {
																	System.out.println(
																			"No movies found in the selected genre.");
																}

															} else {
																System.out.println("No genre Present........");
															}
															break;

														case 3:
															System.out.println(
																	"=====================LOGOUT FROM EXISTING USER================");
															break;

														default:

															System.out.println("Please Enter a valid choice");
															break;

														}
													} while (choice != 3);

												} else {
													System.out.println("No movies found in the selected genre.");
												}

											} else {
												System.out.println("No genre Present........");
											}

											break;

										case 2:
											sc.nextLine();
											System.out.println("Enter the minimum rating (0 to 5):");
											float minRating = sc.nextFloat();
											System.out.println("Enter the maximum rating (0 to 5):");
											float maxRating = sc.nextFloat();

											List<MovieMasterModel> ratingMovies = us.getMoviesByRatingRange(minRating,
													maxRating);
											if (ratingMovies != null && !ratingMovies.isEmpty()) {
												System.out.printf("%-10s | %-20s | %-15s | %-20s%n", "Movie Id",
														"Movie Name", "Release Date", "Rating");
												System.out.println(
														"------------------------------------------------------------");
												ratingMovies.forEach((m) -> {
													System.out.printf("%-10d | %-20s | %-15s | %-20s%n", m.getMovieId(),
															m.getMovTitle(), m.getMovDate(), m.getRating());
													System.out.println(
															"------------------------------------------------------------");
												});

												do {
													System.out.println(
															"=====================================================================");
													System.out.println("Enter 1 for Watch Movie");
													System.out.println("Enter 2 for Search Movies by rating wise");
													System.out.println("Enter 3 for Exit");

													System.out.println("Enter the choice");
													choice = sc.nextInt();

													switch (choice) {

													case 1:

														System.out.println("Enter the movie id for watch the movie");
														mid = sc.nextInt();

														list = ms.searchMovieById(mid);

														if (list != null && !list.isEmpty()) {
															int userId = us.getUserIdByContact(con);

															boolean assigned = us.assignMovieToUser(userId, mid);

															if (assigned) {

																System.out.println(
																		"Thank you for watching movie!..........");

																int umId = us.getMovieUserJoinId(userId, mid);

																System.out.println(
																		"Please enter your rating for the movie (0 to 5):");
																float rating = sc.nextFloat();

																sc.nextLine();

																System.out.println(
																		"Enter the comment on movie (good,bad,very good,average)");
																String text = sc.nextLine();

																boolean ratingAdded = us.addRating(umId, rating, text);

																if (ratingAdded) {
																	System.out.println(
																			"Thank you! Your rating has been recorded.");
																} else {
																	System.out.println("Error recording your rating.");
																}

															} else {
																System.out.println("Error to watch movie to user.");
															}
														} else {
															System.out.println("No movie present.........");

														}

														break;

													case 2:
														sc.nextLine();
														System.out.println("Enter the minimum rating (0 to 5):");
														minRating = sc.nextFloat();
														System.out.println("Enter the maximum rating (0 to 5):");
														maxRating = sc.nextFloat();

														ratingMovies = us.getMoviesByRatingRange(minRating, maxRating);
														if (ratingMovies != null && !ratingMovies.isEmpty()) {
															System.out.printf("%-10s | %-20s | %-15s | %-20s%n",
																	"Movie Id", "Movie Name", "Release Date", "Rating");
															System.out.println(
																	"------------------------------------------------------------");
															ratingMovies.forEach((m) -> {
																System.out.printf("%-10d | %-20s | %-15s | %-20s%n",
																		m.getMovieId(), m.getMovTitle(), m.getMovDate(),
																		m.getRating());
																System.out.println(
																		"------------------------------------------------------------");
															});
														} else {
															System.out.println(
																	"No movies found in the selected rating range.");
														}

														break;
													case 3:
														System.out.println(
																"=====================LOGOUT FROM EXISTING USER================");
														break;
													default:

														System.out.println("Please Enter a valid choice");
														break;

													}
												} while (choice != 3);

											} else {
												System.out.println("No movies found in the selected rating range.");
											}
											break;
											
										case 3:
											    int userId = us.getUserIdByContact(con);
										        sc.nextLine(); 

										        List<MovieMasterModel> watchHistory = us.getWatchHistory(userId);
										        if (watchHistory.isEmpty()) 
										        {
										            System.out.println("No watch history found.");
										        }    System.out.printf("%-10s | %-20s | %-15s | %-10s | %-20s%n", "Movie Id", "Movie Name", "Release Date", "Yours Rating", " Yours Review");
										        System.out.println("-----------------------------------------------------------------------------------");
										        for (MovieMasterModel movie : watchHistory) 
										        {
										            System.out.printf("%-10d | %-20s | %-15s | %-10.2f | %-20s%n", 
										                movie.getMovieId(), movie.getMovTitle(), movie.getMovDate(), movie.getRating(), movie.getText());
										            System.out.println("--------------------------------------------------------------------------------");
										        }
										
											break;

										case 4:
											System.out.println(
													"================EXIT FROM SEARCH MENU OF USER LOGIN=================");
											break;
										default:

											System.out.println("Please Enter a valid choice");
											break;
										}
									} while (choice != 4);

									break;

								case 3:
									movieRepo = new MovieRepository();
								    ratingRepo = new RatingRepository();


								    NaiveBayesService naiveBayesService = new NaiveBayesService(movieRepo, ratingRepo);

								    System.out.println("Enter How many Top Movies You want ?");
								    int topN=sc.nextInt();
								    

								    // Call recommendMovies method
								    List<MovieRating> recommendedMovies = naiveBayesService.recommendMovies(topN);

								    // Display recommended movies with their ratings
							        System.out.println("Top " + topN + " movies:");
							        for (int i = 0; i < recommendedMovies.size(); i++) 
							        {
							            MovieRating movieRating = recommendedMovies.get(i);
							            System.out.println((i + 1) + ". " + movieRating.getMovie().getMovTitle() +
							                               " (Rating: " + movieRating.getAverageRating() + ")");
							        }

						
					           
					          
									
									
								break;
								case 4:

									System.out.println("=====================LOGOUT FROM EXISTING USER================");
									break;
								default:

									System.out.println("Please Enter a valid choice");
									break;
								}
							} while (choice != 4);
						} else {

							System.out.println("Please enter a valid username or password............");
							System.out.println(" Do you Forgot Password and want to change it");
							String msg = sc.nextLine();
							if (msg.equals("yes")) {
								System.out.println("Enter the username(Contact Number) to change the password ");
								String user = sc.nextLine();

								if (us.checkTheUsername(user)) {
									System.out.println("Enter the new password for the admin login");
									String newPass = sc.nextLine();

									b = us.changePass(user, newPass);
									if (b) {
										System.out.println("Password Chnages Successfully..........");
									} else {
										System.out.println("Some error in changing password....");
									}

								} else {
									System.out.println("Please Enter a valid Username............");
								}

							} else {
								System.out.println("Thank you!........");
							}
						}

						break;
						
					

					case 3:
						System.out.println("==========================EXIT FROM USER LOGIN=========================");
						break;
					default:

						System.out.println("Please Enter a valid choice");
						break;
					}
				} while (choice != 3);

				// User Login End
			}

		} while (true);

	}

}
