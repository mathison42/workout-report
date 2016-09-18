import com.google.api.services.sheets.v4.model.ValueRange
import com.google.api.services.sheets.v4.Sheets

import com.seven.discs.client.BatchUpdateValues
import com.seven.discs.client.DateHelper
import com.seven.discs.client.SheetInstance
import com.seven.discs.client.SheetTemplate
import com.seven.discs.client.SheetsAuthenticator
import com.seven.discs.client.SpreadsheetInstance


public class SheetsRunner {
  final static String Application_Name = "Test Runner"  // Name of your Test Application
  final static String Data_Store_Dir = ".credentials/sheets.googleapis.com-groovy-wrapper-test.json" //Location to save cookie credentials
  static Sheets service

    public static void main(String[] args) {
      println "[Start]..."
      SheetsAuthenticator sa = new SheetsAuthenticator(Application_Name, Data_Store_Dir)
      service = sa.getSheetsService()
      SpreadsheetInstance ssi = new SpreadsheetInstance(service)
      println "Values: " + SheetTemplate.values();

      // Create First Page of new Spreadsheet
      SheetInstance si1 = new SheetInstance(ssi, 0)
      si1.updateSheetProps(SheetTemplate.DAY_COUNTER.getRequest())

      // Create Second Page of previous Spreadsheet
      SheetInstance si2 = new SheetInstance(ssi)
      si2.createSheet(SheetTemplate.FULL_RECORD.getRequest())

      // Create Third* Page of previous Spreadsheet
      SheetInstance si3 = new SheetInstance(ssi)
      si3.createSheet(SheetTemplate.DAY_RECORD.getRequest())

      ssi.updateSpreadsheet();
      ssi.updateSheets();
      BatchUpdateValues vals = new BatchUpdateValues();
      println "Title1: " + si1.getTitle();
      vals.createXAxisHeader(si1.getTitle(), ["Checked In", "Location"]);
      vals.createYAxisHeader(si1.getTitle(), DateHelper.getNextXDates(10));
      println "Title2: " + si2.getTitle();
      vals.createXAxisHeader(si2.getTitle(), ["Dumbbells", "Dumbbell Lunges", "Bench Press", "Back Squat", "Front Squat"]);
      vals.createYAxisHeader(si2.getTitle(), DateHelper.getNextXDates(20));
      println "Title3: " + si3.getTitle();
      vals.createXAxisHeader(si3.getTitle(), ["Dumbbell Lunges", "Back Squat", "Front Squat"]);
      vals.createYAxisHeader(si3.getTitle(), DateHelper.getNextXDates(30));
      vals.setValueRange2Request();
      ssi.batchUpdateValues(vals.getRequest());

      si1.setAxes();
      si2.setAxes();
      si3.setAxes();
      println "SI1: X: " + si1.getXAxis().toString();
      println "SI1: Y: " + si1.getYAxis().toString();

      println si1.getLastXIndex();
      println si1.getLastYIndex();
      println si2.getLastXIndex();
      println si2.getLastYIndex();
      println si3.getLastXIndex();
      println si3.getLastYIndex();

      BatchUpdateValues exerciseVals = new BatchUpdateValues();
      exerciseVals.addLocation(si1, "Home");
      exerciseVals.addExercise(si2, "Back Squat", "180x10:190x10:200x10");
      exerciseVals.addExercise(si3, "Front Squat", "90x10:95x10:100x10");
      exerciseVals.setValueRange2Request();
      ssi.batchUpdateValues(exerciseVals.getRequest());
      println "...[End]"

    }
  }
