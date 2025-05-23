🚂 IRCTC Ticket Booking App (Pure Java Backend) 🎫

✨ Project Overview

Welcome to the IRCTC Ticket Booking App, a robust backend system meticulously crafted in Pure Java! This project simulates the core functionalities of a train ticket booking platform, designed with a strong emphasis on Low-Level Design (LLD) principles. Forget complex frameworks; this is a deep dive into clean architecture, efficient data handling, and solid Java programming.

It's a perfect showcase for understanding how to build scalable and maintainable backend services from the ground up, managing users, trains, and bookings without external frameworks like Spring Boot.

🚀 Key Features

User Management:

📝 Sign Up: Create new user accounts.

🔑 Login: Secure user authentication.

👤 User Profiles: Basic user data management.

Train Operations:

🔍 Search Trains: Find trains between specified source and destination stations.

🗺️ View Train Details: Get comprehensive information about a train's route, schedule, and seating.

💺 Seat Availability: Check real-time seat availability for trains.

Booking System:

✅ Book Seats: Reserve seats on available trains.

📜 Fetch Bookings: View a user's entire booking history.

❌ Cancel Booking: (Planned) Functionality to cancel existing tickets.

Data Persistence:

💾 Local JSON Database: Data is persisted to JSON files for simplicity and easy inspection.

🛠️ Technologies Used

Java Development Kit (JDK) 17+: The core language and runtime environment.

Jackson Databind: For seamless JSON serialization and deserialization of application data.

jBCrypt: For secure hashing and verification of user passwords.

Standard Java Utilities: java.util.List, java.util.Map, java.util.UUID, java.util.logging, etc.

💡 Low-Level Design (LLD) Focus

This project is built with a strong emphasis on LLD, demonstrating:

Object-Oriented Principles (OOP): Clear separation of concerns with well-defined classes (User, Train, Ticket, UserBookingService, TrainService).

Encapsulation: Data hiding and controlled access through getters and setters.

Modularity: Code organized into logical packages (entities, service, util).

Robust Error Handling: Custom exceptions and try-catch blocks for graceful failure management (e.g., IOException for file operations, IllegalArgumentException for invalid inputs).

Utility Classes: UserServiceUtil for reusable, static functionalities like password hashing.

Data Layer Abstraction: Services (UserBookingService, TrainService) handle data access and business logic, abstracting away the underlying JSON file operations.

🚀 Getting Started

Follow these steps to get the project up and running on your local machine.

Prerequisites

Java Development Kit (JDK) 17 or higher installed on your system.

Git for cloning the repository.

Maven or Gradle (if you plan to manage dependencies, though this project uses direct JARs for simplicity).

Jackson Databind and jBCrypt JARs: You'll need to download these and add them to your classpath manually or set up a simple build tool.

Jackson Databind (core, annotations, databind)

jBCrypt

Installation & Setup

Clone the repository:

git clone https://github.com/Arpreet2107/irctc-ticket-booking-app.git

cd IRCTC-Ticket-Booking-App


(Replace your-username/IRCTC-Ticket-Booking-App with your actual repository path)

Create localDB Directory:
The application uses JSON files for data persistence. Create a directory named localDB at the root of your project:

mkdir localDB


The application will create users.json and trains.json inside this directory upon first run or data modification.

Add Dependencies to Classpath:
Since this is a pure Java project without a build tool like Maven/Gradle, you'll need to manually add the Jackson and jBCrypt JARs to your classpath when compiling and running.

Example (adjust paths to your downloaded JARs):

# JARs are in a 'lib' folder at your project root
mkdir lib
# Download jackson-core.jar, jackson-databind.jar, jackson-annotations.jar, jbcrypt.jar into 'lib'


Compiling the Project

Navigate to the project's root directory in your terminal and compile all Java files, including the necessary JARs in the classpath:

javac -cp "lib/*" ticket/booking/*.java ticket/booking/entities/*.java ticket/booking/service/*.java ticket/booking/util/*.java


(Adjust lib/* if your JARs are in a different location)

Running the Application

After successful compilation, run the App class from the project root:

java -cp ".;lib/*" ticket.booking.App  # For Windows
java -cp ".:lib/*" ticket.booking.App  # For Linux/macOS


(The . in the classpath includes the current directory where your compiled .class files reside)

🖥️ How to Use

Once the application is running, you'll interact with it via the command line:

Main Menu:

Choose 1 to Login if you have an existing account.

Choose 2 to Sign Up for a new account.

Choose 3 to Exit the application.

User Menu (After Login):

1. Fetch Bookings: View your booked tickets.

2. Search Trains: Find trains between stations and potentially book a seat.

3. Cancel Booking: (Future feature) Cancel a ticket by ID.

4. Logout: Return to the main menu.

Example Flow:

Welcome to the Train Ticket Booking System!
1. Login
2. Sign Up
3. Exit
   Enter your choice: 2
   Enter your name: newuser
   Enter your password: password123
   Sign up successful. Please log in.

Welcome to the Train Ticket Booking System!
1. Login
2. Sign Up
3. Exit
   Enter your choice: 1
   Enter your name: newuser
   Enter your password: password123
   Login successful!

User Menu:
1. Fetch Bookings
2. Search Trains
3. Cancel Booking
4. Logout
   Enter your choice: 2
   Enter source station: bangalore
   Enter destination station: delhi

Available Trains:
1. Train ID: t101, Train Name: Bangalore Express, Train No: 12345
   Stations and Times:
   Station: bangalore, Time: 13:50:00
   Station: chennai, Time: 18:30:00
   Station: vijayawada, Time: 23:00:00
   Station: delhi, Time: 08:00:00
   Select a train by typing the corresponding number: 1
   Available Seats (0 = Available, 1 = Booked):
   Row 1: [0, 0, 0, 0, 0, 0]
   Row 2: [0, 1, 0, 0, 0, 0]
   Row 3: [0, 0, 0, 1, 0, 0]
   Row 4: [0, 0, 0, 0, 0, 0]
   Enter the row (starting from 1): 1
   Enter the column (starting from 1): 1
   Booking your seat....
   Booked! Enjoy your journey.
   Ticket ID: <generated-uuid>

User Menu:
1. Fetch Bookings
2. Search Trains
3. Cancel Booking
4. Logout
   Enter your choice: 1
   Tickets Booked for User: newuser
   Ticket ID: <generated-uuid>, Source: bangalore, Destination: delhi, Travel Date: 2024-05-01, Train ID: t101, Train Name: Bangalore Express

... and so on.


📂 Project Structure

IRCTC-Ticket-Booking-App/
├── lib/                      # Directory for external JAR dependencies (Jackson, jBCrypt)
├── localDB/                  # JSON files for data persistence (users.json, trains.json)
│   ├── users.json
│   └── trains.json
├── ticket/
│   └── booking/
│       ├── App.java          # Main application entry point
│       ├── entities/
│       │   ├── Ticket.java   # Represents a booked ticket
│       │   ├── Train.java    # Represents a train with its details
│       │   └── User.java     # Represents a user account
│       ├── service/
│       │   ├── TrainService.java      # Handles train data operations
│       │   └── UserBookingService.java # Manages user and booking logic
│       └── util/
│           └── UserServiceUtil.java   # Utility for password hashing
├── .gitignore                # Specifies files/folders to be ignored by Git
└── README.md                 # This file!


🌟 Future Enhancements

Robust Error Handling: More specific custom exceptions for different scenarios.

Date Handling: Use java.time.LocalDate properly for dateOfTravel in Ticket and implement date-based search.

Cancellation Logic: Fully implement ticket cancellation, updating seat availability.

Admin Features: Add functionalities for managing trains, routes, and schedules.

Real Database Integration: Migrate from JSON files.

Build Automation: Introduce Gradle for dependency management and build automation.

🤝 Contributing

Contributions are welcome! If you have ideas for improvements, bug fixes, or new features, please feel free to:

Fork the repository.

Create a new branch (git checkout -b feature/your-feature).

Make your changes.

Commit your changes (git commit -m 'feat: Add new feature').

Push to the branch (git push origin feature/your-feature).

Open a Pull Request.



📞 Contact

For any questions or feedback, feel free to reach out:

Arpreet Mahala

Email: [arpreet4114@gmail.com]