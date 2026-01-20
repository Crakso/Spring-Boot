INSERT INTO tbl_profile (name, email, password, is_active)
VALUE ("neeraj bisht","neeraj@gmail.com","123456",true),
("akki bisht","akki@gmail.com","123456",true),
("nitin","nitin@gmail.com","123456",true),
("nisha","nisha@gmail.com","123456",true),
("lucky","lucky@gmail.com","123456",true);


INSERT INTO tbl_todos (topic, content, profile_id)
VALUE
("Buying Vegetables", "1kg potato , 2kg Tomato, 5kg Sugar", 1),
('Pick up prescription', 'Pharmacy closes at 6 PM. Need to get the new medication.', 2),
('Schedule annual check-up', 'Call clinic to book an appointment with Dr. Smith.', 3),
('Physical therapy session', 'Appointment at the rehab center at 2 PM today.', 2),
('Dietary consultation', 'Meeting with the nutritionist to discuss meal plans.',4),
('Follow-up on insurance claim', 'Call the insurance provider regarding the pending claim for patient 1.', 2);
