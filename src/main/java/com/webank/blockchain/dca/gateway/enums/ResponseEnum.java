package com.webank.blockchain.dca.gateway.enums;

import com.webank.blockchain.dca.gateway.constant.SystemConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
    Success(0, "000000000", "success"),
    Request_Timeout(1, SystemConstant.SUB_SYSTEM_NO + "U00000001", "Timeout"),
    Request_Error(4, SystemConstant.SUB_SYSTEM_NO + "U00000004", "Request Error"),
    Invalid_Request_Param(2, SystemConstant.SUB_SYSTEM_NO + "T00000002", "Invalid request param"),
    Request_Rate_Limit(3, SystemConstant.SUB_SYSTEM_NO + "T00000003", "request rate limit"),
    Invalid_File_Type(10001, SystemConstant.SUB_SYSTEM_NO + "B00010001", "invalid file type"),
    Invalid_File_Stream(10002, SystemConstant.SUB_SYSTEM_NO + "T00010002", "invalid file stream"),
    Invalid_File_Uri(10003, SystemConstant.SUB_SYSTEM_NO + "T00010003", "invalid file uri"),
    Invalid_File_Format(10005, SystemConstant.SUB_SYSTEM_NO + "B00010005", "invalid file format"),
    Invalid_File_Size(10006, SystemConstant.SUB_SYSTEM_NO + "B00010006", "invalid file size"),
    Download_File_Error(10007, SystemConstant.SUB_SYSTEM_NO + "T00010007", "download file error"),
    Invalid_File_ID(10008, SystemConstant.SUB_SYSTEM_NO + "B00010008", "invalid file ID"),
    Duplicate_Data_Item(10011, SystemConstant.SUB_SYSTEM_NO + "T00010011", "duplicate data item"),
    Exist_Unique_ID(10012, SystemConstant.SUB_SYSTEM_NO + "T00010012", "uniqueId already exist"),
    No_Unique_ID(10013, SystemConstant.SUB_SYSTEM_NO + "T00010013", "no uniqueId"),
    Add_Watermark_ERROR(10014, SystemConstant.SUB_SYSTEM_NO + "T00010014", "add watermark error"),
    Store_ERROR(10015, SystemConstant.SUB_SYSTEM_NO + "T00010015", "store error"),
    Image_Not_Support(10016, SystemConstant.SUB_SYSTEM_NO + "T00010016", "Image pixels minimum 2000"),
    AppId_Not_Exist(10017, SystemConstant.SUB_SYSTEM_NO + "T00010017", " app id not exsit"),
    UniqueID_Not_Exist(10018, SystemConstant.SUB_SYSTEM_NO + "T00010018", "uniqueId not exist"),
    Match_Error(10019, SystemConstant.SUB_SYSTEM_NO + "T00010019", "match service error"),
    Auth_Watermark_ERROR(10020, SystemConstant.SUB_SYSTEM_NO + "T00010020", "auth watermark error"),
    FILE_HASH_EXIST(10030, SystemConstant.SUB_SYSTEM_NO + "B00010030", "watermark file hash exist"),
    WM_FILE_SIZE_OVER(10031, SystemConstant.SUB_SYSTEM_NO + "B00010031", "add watermark file size over 10MB"),
    INTERNAL_ERROR(10500, SystemConstant.SUB_SYSTEM_NO + "U00010500", "internal error"),
    No_App_Id(10051, SystemConstant.SUB_SYSTEM_NO + "B00010051", "no app id"),
    No_Collection_Id(10052, SystemConstant.SUB_SYSTEM_NO + "B00010052", "no collection id");

    private int code;
    private String responseCode;
    private String message;

    public static ResponseEnum parse(String responseCode) {
        for (ResponseEnum e : ResponseEnum.values()) {
            if (e.getResponseCode().equals(responseCode)) {
                return e;
            }
        }
        return INTERNAL_ERROR;
    }
}
