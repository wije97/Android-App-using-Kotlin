package com.example.childapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper


class SQLiteHelper

internal constructor(context: Context?, name: String?, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    fun queryData(sql: String?) {
        val database = writableDatabase
        database.execSQL(sql)
    }


    fun insertData(fname: String?, lname: String?, dob: String?, gender: String?, image: ByteArray?, yearcount: String?, monthcount: String?) {
        val database = writableDatabase

        val sql = "INSERT INTO ChildData VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)"
        val statement = database.compileStatement(sql)
        statement.clearBindings()
        statement.bindString(1, fname)
        statement.bindString(2, lname)
        statement.bindString(3, dob)
        statement.bindString(4, gender)
        statement.bindBlob(5, image)
        statement.bindString(6, yearcount)
        statement.bindString(7, monthcount)
        statement.executeInsert()
    }

    fun getProfilesCount(): Int {
        val countQuery = "SELECT  * FROM   ChildData"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun deleteData(id: Int) {
        val database = writableDatabase

        val sql = "DELETE FROM ChildData WHERE id=?"
        val statement = database.compileStatement(sql)
        statement.clearBindings()
        statement.bindDouble(1, id.toDouble())
        statement.execute()
        database.close()
    }

    fun deleteDatavc(id: Int) {
        val database = writableDatabase

        val sql = "DELETE FROM vaccinCheckTable WHERE vc_id=?"
        val statement = database.compileStatement(sql)
        statement.clearBindings()
        statement.bindDouble(1, id.toDouble())
        statement.execute()
        database.close()
    }

    fun getData(sql: String?): Cursor {
        val database = readableDatabase
        return database.rawQuery(sql, null)
    }

    fun queryData2(sql2: String?) {
        val database2 = writableDatabase
        database2.execSQL(sql2)
    }
    fun insertDataAM(title: String?, des: String?, date: String?, time: String?) {
        val database2 = writableDatabase

        val sql2 = "INSERT INTO AlarmData VALUES(NULL, ?, ?, ?, ?)"
        val statement = database2.compileStatement(sql2)
        statement.clearBindings()
        statement.bindString(1, title)
        statement.bindString(2, des)
        statement.bindString(3, date)
        statement.bindString(4, time)
        statement.executeInsert()
    }

    fun updateDataAM(title: String?, des: String?, date: String?, time: String?, id: Int) {

        val database2 = writableDatabase

        val sql2 = "UPDATE AlarmData SET title=?, des=?, date=?, time=? WHERE id=?"
        val statement = database2.compileStatement(sql2)
        statement.bindString(1, title)
        statement.bindString(2, des)
        statement.bindString(3, date)
        statement.bindString(3, time)
        statement.bindDouble(5, id.toDouble())
        statement.execute()
        database2.close()
    }

    fun deleteDataAM(id: Int) {
        val database2 = writableDatabase

        val sql2 = "DELETE FROM AlarmData WHERE id=?"
        val statement = database2.compileStatement(sql2)
        statement.clearBindings()
        statement.bindDouble(1, id.toDouble())
        statement.execute()
        database2.close()
    }

    fun getDataAM(sql2: String?): Cursor {
        val database2 = readableDatabase
        return database2.rawQuery(sql2, null)
    }


    companion object{

        private  val TABLE_NAME = "Records"
        private val COL_ID = "Id"
        private val COL_DOM = "DateofMes"
        private val COL_WEIGHT = "Weight"
        private val COL_HEIGHT = "Height"
        private val COL_MONTHCOUNT = "MonthCount"
        private val COL_CID= "Child_ID"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME" + "($COL_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, $COL_DOM TEXT, $COL_WEIGHT TEXT, $COL_HEIGHT TEXT, $COL_MONTHCOUNT TEXT, $COL_CID INTEGER, FOREIGN KEY($COL_CID) REFERENCES ChildData(id))")

        val createTable = "create table waTable(w_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Age REAL, Weight REAL, child_id INTEGER, FOREIGN KEY(child_id) REFERENCES ChildData(id))"
        db?.execSQL(createTable)

        val createTable2 = "create table haTable(h_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,Age REAL, Height REAL, child_id INTEGER, FOREIGN KEY(child_id) REFERENCES ChildData(id))"
        db?.execSQL(createTable2)

        val createTable3 = "create table whTable(wh_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Height REAL, Weight REAL, child_id INTEGER, FOREIGN KEY(child_id) REFERENCES ChildData(id))"
        db?.execSQL(createTable3)

        val createTable4 = "create table bmiaTable(b_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Age REAL, BMI REAL, child_id INTEGER, FOREIGN KEY(child_id) REFERENCES ChildData(id))"
        db?.execSQL(createTable4)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertRecordData(recorddt:RecordDT){
        val db = this.writableDatabase
        val values= ContentValues()
        values.put(COL_DOM,recorddt.dateofmes)
        values.put(COL_WEIGHT,recorddt.weightdt)
        values.put(COL_HEIGHT,recorddt.heightdt)
        values.put(COL_MONTHCOUNT,recorddt.monthcount)
        values.put(COL_CID,recorddt.child_id)

        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun insertDataWA(age: Float, weight: Float, cid: Int): Boolean {
        val db = this.writableDatabase
        val contentvalues = ContentValues()
        contentvalues.put("Age", age)
        contentvalues.put("Weight", weight)
        contentvalues.put("child_id",cid)
        db.insert("waTable", null, contentvalues)
        return true
    }

    fun insertDataHA(age: Float, height: Float, cid: Int): Boolean {
        val db = this.writableDatabase
        val contentvalues = ContentValues()
        contentvalues.put("Age", age)
        contentvalues.put("Height", height)
        contentvalues.put("child_id",cid)
        db.insert("haTable", null, contentvalues)
        return true
    }
    fun insertDataWH(height: Float, weight: Float, cid: Int): Boolean {
        val db = this.writableDatabase
        val contentvalues = ContentValues()
        contentvalues.put("Height", height)
        contentvalues.put("Weight", weight)
        contentvalues.put("child_id",cid)
        db.insert("whTable", null, contentvalues)
        return true
    }

    fun insertDataBA(age: Float, bmi: Float, cid: Int): Boolean {
        val db = this.writableDatabase
        val contentvalues = ContentValues()
        contentvalues.put("Age", age)
        contentvalues.put("BMI", bmi)
        contentvalues.put("child_id",cid)
        db.insert("bmiaTable", null, contentvalues)
        return true
    }

    fun vaccinCheck(bcg:Int, opv1: Int, opv2: Int, opv3: Int,mmr1: Int,lje: Int,opv4: Int,mmr2: Int,opv5:Int,hpv1: Int,hpv2: Int,atd: Int,rubella: Int, cid: Int){
        val db = this.writableDatabase
        val contentvalues = ContentValues()
        contentvalues.put("BCG", bcg)
        contentvalues.put("OPV1", opv1)
        contentvalues.put("OPV2", opv2)
        contentvalues.put("OPV3", opv3)
        contentvalues.put("MMR1", mmr1)
        contentvalues.put("LJE", lje)
        contentvalues.put("OPV4", opv4)
        contentvalues.put("MMR2", mmr2)
        contentvalues.put("OPV5", opv5)
        contentvalues.put("HPV1", hpv1)
        contentvalues.put("HPV2", hpv2)
        contentvalues.put("aTD", atd)
        contentvalues.put("RUBELLA", rubella)
        contentvalues.put("child_id", cid)
        db.insert("vaccinCheckTable", null, contentvalues)
        db.close()
    }

    fun vaccinCheckUp(bcg:Int, opv1:Int, opv2: Int, opv3: Int,mmr1: Int,lje: Int,opv4: Int,mmr2: Int,opv5:Int,hpv1: Int,hpv2: Int,atd: Int,rubella: Int, vc_id:Int){

        val db = this.writableDatabase
        val contentvalues = ContentValues()
        contentvalues.put("BCG", bcg)
        contentvalues.put("OPV1", opv1)
        contentvalues.put("OPV2", opv2)
        contentvalues.put("OPV3", opv3)
        contentvalues.put("MMR1", mmr1)
        contentvalues.put("LJE", lje)
        contentvalues.put("OPV4", opv4)
        contentvalues.put("MMR2", mmr2)
        contentvalues.put("OPV5", opv5)
        contentvalues.put("HPV1", hpv1)
        contentvalues.put("HPV2", hpv2)
        contentvalues.put("aTD", atd)
        contentvalues.put("RUBELLA", rubella)
        db.update("vaccinCheckTable", contentvalues, "child_id=?", arrayOf(vc_id.toString()))
        db.close()
    }

    fun getDataVaccine(sql3: String?): Cursor {
        val database3 = readableDatabase
        return database3.rawQuery(sql3, null)
    }

    fun vaccineDate(bcg:String, opv1: String, opv2: String, opv3: String,mmr1: String,lje: String,opv4: String,mmr2: String,opv5: String,hpv1: String,hpv2: String,atd: String,rubella: String,cid: Int){
        val db = this.writableDatabase
        val contentvalues = ContentValues()
        contentvalues.put("BCG", bcg)
        contentvalues.put("OPV1", opv1)
        contentvalues.put("OPV2", opv2)
        contentvalues.put("OPV3", opv3)
        contentvalues.put("MMR1", mmr1)
        contentvalues.put("LJE", lje)
        contentvalues.put("OPV4", opv4)
        contentvalues.put("MMR2", mmr2)
        contentvalues.put("OPV5", opv5)
        contentvalues.put("HPV1", hpv1)
        contentvalues.put("HPV2", hpv2)
        contentvalues.put("aTD", atd)
        contentvalues.put("RUBELLA", rubella)
        contentvalues.put("child_id", cid)
        db.insert("vaccineDateTable", null, contentvalues)
        db.close()
    }

    fun vaccineDateUp(bcg:String, opv1: String, opv2: String, opv3: String,mmr1: String,lje: String,opv4: String,mmr2: String,opv5: String,hpv1: String,hpv2: String,atd: String,rubella: String, vc_id: Int){

        val db = this.writableDatabase
        val contentvalues = ContentValues()
        contentvalues.put("BCG", bcg)
        contentvalues.put("OPV1", opv1)
        contentvalues.put("OPV2", opv2)
        contentvalues.put("OPV3", opv3)
        contentvalues.put("MMR1", mmr1)
        contentvalues.put("LJE", lje)
        contentvalues.put("OPV4", opv4)
        contentvalues.put("MMR2", mmr2)
        contentvalues.put("OPV5", opv5)
        contentvalues.put("HPV1", hpv1)
        contentvalues.put("HPV2", hpv2)
        contentvalues.put("aTD", atd)
        contentvalues.put("RUBELLA", rubella)
        db.update("vaccineDateTable", contentvalues, "child_id=?", arrayOf(vc_id.toString()))
        db.close()
    }

    fun getDataVaccineDate(sql3: String?): Cursor {
        val database3 = readableDatabase
        return database3.rawQuery(sql3, null)
    }
}