class Address:
    def __init__(self, street, city, zip_code):
        self.street = street
        self.city = city
        self.zip_code = zip_code

    def __str__(self):
        return f"{self.street}, {self.city}, {self.zip_code}"


class Student:
    def __init__(self, name, age, address):
        self.name = name
        self.address = address
        self.courses = []
        self.age = age

    @property
    def age(self):
        return self._age

    @age.setter
    def age(self, value):
        if not isinstance(value, int) or value < 0:
            raise ValueError("Age must be a positive integer.")
        self._age = value

    def add_course(self, course_name):
        self.courses.append(course_name)

    def display(self):
        print(f"--- Student Record: {self.name} ---")
        print(f"Age:     {self.age}")
        print(f"Address: {self.address}")
        print(f"Courses: {', '.join(self.courses) if self.courses else 'No courses enrolled'}")


class ScholarshipStudent(Student):
    def __init__(self, name, age, address, scholarship_amount):
        super().__init__(name, age, address)
        self.scholarship_amount = scholarship_amount

    def display(self):
        super().display()
        print(f"Funding: ${self.scholarship_amount:,.2f} Scholarship")


if __name__ == "__main__":
    addr1 = Address("123 Tech Lane", "San Francisco", "94105")
    s1 = Student("Alice Smith", 20, addr1)
    s1.add_course("Introduction to Python")
    s1.add_course("Data Structures")
    
    addr2 = Address("456 Grant Ave", "Boston", "02108")
    s2 = ScholarshipStudent("Bob Miller", 22, addr2, 5000)
    s2.add_course("Advanced Algorithms")

    s1.display()
    print("\n")
    s2.display()

    print("\n--- Testing Age Validation ---")
    try:
        s1.age = -5
    except ValueError as e:
        print(f"Caught expected error: {e}")