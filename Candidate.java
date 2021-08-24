package sol;

import src.IAttributeDatum;

import java.util.Objects;

public class Candidate implements IAttributeDatum {
  String gender;
  boolean leadershipExperience;
  String lastPositionDuration;
  String numWorkExperiences;
  String programmingLanguages;
  String gpa;
  String location;
  boolean hired;


  public Candidate(String gender, boolean leadershipExperience,
                   String lastPositionDuration, String numWorkExperiences,
                   String programmingLanguages, String gpa, String location, boolean hired) {
    this.gender = gender;
    this.leadershipExperience = leadershipExperience;
    this.lastPositionDuration = lastPositionDuration;
    this.numWorkExperiences = numWorkExperiences;
    this.programmingLanguages = programmingLanguages;
    this.gpa = gpa;
    this.location = location;
    this.hired = hired;
  }

  public Candidate(String[] args) {
    if (args.length != 8 && args.length != 7) {
      throw new IllegalArgumentException(
          "Incorrect number of args for Candidate class");
    }
    if(args.length == 8) {
      this.gender = args[0];
      this.leadershipExperience = Boolean.parseBoolean(args[1]);
      this.lastPositionDuration = args[2];
      this.numWorkExperiences = args[3];
      this.programmingLanguages = args[4];
      this.gpa = args[5];
      this.location = args[6];
      this.hired = Boolean.parseBoolean(args[7]);
    }else{
      this.leadershipExperience = Boolean.parseBoolean(args[0]);
      this.lastPositionDuration = args[1];
      this.numWorkExperiences = args[2];
      this.programmingLanguages = args[3];
      this.gpa = args[4];
      this.location = args[5];
      this.hired = Boolean.parseBoolean(args[6]);
    }
  }

  @Override
  public Object getValueOf(String attributeName) {
    switch (attributeName) {
      case "gender":
        return this.gender;
      case "leadershipExperience":
        return this.leadershipExperience;
      case "lastPositionDuration":
        return this.lastPositionDuration;
      case "numWorkExperiences":
        return this.numWorkExperiences;
      case "programmingLanguages":
        return this.programmingLanguages;
      case "gpa":
        return this.gpa;
      case "location":
        return this.location;
      case "hired":
        return this.hired;
      default:
        throw new RuntimeException(
            "Unknown field " + attributeName + "in Candidate class");
    }
  }

  @Override
  public String toString() {
    return "Candidate{" + '\'' + ", gender='"
            + this.gender + '\'' + ", leadershipExperience="
            + this.leadershipExperience + ", lastPositionDuration="
            + this.lastPositionDuration + ", numWorkExperiences="
            + this.numWorkExperiences + ", programmingLanguages="
            + this.programmingLanguages + ", gpa=" + this.gpa + ", location='"
            + this.location + ", hired="+ this.hired + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Candidate candidate = (Candidate) o;
    return this.leadershipExperience == candidate.leadershipExperience
            && this.lastPositionDuration.equals(candidate.lastPositionDuration)
            && this.numWorkExperiences == candidate.numWorkExperiences
            && this.programmingLanguages == candidate.programmingLanguages
            && this.gpa.equals(candidate.gpa) && this.gender
            .equals(candidate.gender) && this.location
            .equals(candidate.location) && this.hired==candidate.hired;
  }
}
