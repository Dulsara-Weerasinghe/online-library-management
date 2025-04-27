package com.Assessment.Library_management.dto;

import com.Assessment.Library_management.util.MessageVarList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean {
    private String status;
    private String message;
    private Object content;

    public void setResponse(String status) {
        this.status = status;
        switch (status) {
            case MessageVarList.RSP_SUCCESS:
                this.message = "Success";
                break;
            case MessageVarList.RSP_NO_DATA_FOUND:
                this.message = "No Data Found";
                break;
            case MessageVarList.RSP_NOT_AUTHORISED:
                this.message = "Unauthorised Action";
                break;
            case MessageVarList.RSP_TOKEN_EXPIRED:
                this.message = "Token Expired";
                break;
        }
    }
}
