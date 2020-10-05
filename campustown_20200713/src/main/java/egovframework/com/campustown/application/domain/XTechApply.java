package egovframework.com.campustown.application.domain;

import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.support.pagination.PagingSearch;
import oracle.sql.DATE;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;


public class XTechApply extends PagingSearch {
    private Integer applySeq;
    private String applyRegId;
    private Date applyRegDate;

    private String itemName;
    private String joinGubun;
    private String companyName;
    private String setUpDate;
    private String companyPhoneNum;
    private String companyAddr;
    private String companyLicense;

    private String ownerName;
    private String ownerPhoneNum;
    private String ownerBirth;
    private String ownerEmail;
    private String ownerAddr;

    private List<String> memName;
    private List<String> memBirth;
    private List<String> memCompany;
    private List<String> memPosition;
    private List<String> memPhoneNum;
    private List<String> memEmail;

    private String memNameStr = "";
    private String memBirthStr = "";
    private String memCompanyStr = "";
    private String memPositionStr = "";
    private String memPhoneNumStr = "";
    private String memEmailStr = "";


    private String applyRoot;
    private String space;

    private List<XTechApplyMember> memberList = new ArrayList<>();

    public void setData(UserMember currentUser, HttpServletRequest req) throws IOException {
        this.setApplyRegId(currentUser.getUserId());


        if(this.getMemName() != null && this.getMemName().size() > 0){
            for(int i=0;i <= this.getMemName().size()-1;i++){
                this.setMemNameStr(this.getMemNameStr().concat(this.getMemName().get(i)+";"));
                this.setMemBirthStr(this.getMemBirthStr().concat(this.getMemBirth().get(i)+";"));
                this.setMemCompanyStr(this.getMemCompanyStr().concat(this.getMemCompany().get(i)+";"));
                this.setMemPositionStr(this.getMemPositionStr().concat(this.getMemPosition().get(i)+";"));
                this.setMemPhoneNumStr(this.getMemPhoneNumStr().concat(this.getMemPhoneNum().get(i)+";"));
                this.setMemEmailStr(this.getMemEmailStr().concat(this.getMemEmail().get(i)+";"));
            }
        }
    }
    public void Processing() throws IOException {

        if(this.getMemNameStr() != null){
            String[] name = this.getMemNameStr().split(";");
            String[] birth = this.getMemBirthStr().split(";");
            String[] company = this.getMemCompanyStr().split(";");
            String[] position = this.getMemPositionStr().split(";");
            String[] phoneNum = this.getMemPhoneNumStr().split(";");
            String[] email = this.getMemEmailStr().split(";");

            for(int i=0; i <= name.length-1;i++){
                XTechApplyMember xTechApplyMember = new XTechApplyMember();
                xTechApplyMember.setMemName(name[i]);
                xTechApplyMember.setMemBirth(birth[i]);
                xTechApplyMember.setMemCompany(company[i]);
                xTechApplyMember.setMemPosition(position[i]);
                xTechApplyMember.setMemPhoneNum(phoneNum[i]);
                xTechApplyMember.setMemEmail(email[i]);
                this.memberList.add(xTechApplyMember);
            }
        }
    }

    public Date getApplyRegDate() {
        return applyRegDate;
    }

    public void setApplyRegDate(Date applyRegDate) {
        this.applyRegDate = applyRegDate;
    }

    public String getMemNameStr() {
        return memNameStr;
    }

    public void setMemNameStr(String memNameStr) {
        this.memNameStr = memNameStr;
    }

    public String getMemBirthStr() {
        return memBirthStr;
    }

    public void setMemBirthStr(String memBirthStr) {
        this.memBirthStr = memBirthStr;
    }

    public String getMemCompanyStr() {
        return memCompanyStr;
    }

    public void setMemCompanyStr(String memCompanyStr) {
        this.memCompanyStr = memCompanyStr;
    }

    public String getMemPositionStr() {
        return memPositionStr;
    }

    public void setMemPositionStr(String memPositionStr) {
        this.memPositionStr = memPositionStr;
    }

    public String getMemPhoneNumStr() {
        return memPhoneNumStr;
    }

    public void setMemPhoneNumStr(String memPhoneNumStr) {
        this.memPhoneNumStr = memPhoneNumStr;
    }

    public String getMemEmailStr() {
        return memEmailStr;
    }

    public void setMemEmailStr(String memEmailStr) {
        this.memEmailStr = memEmailStr;
    }

    public Integer getApplySeq() {
        return applySeq;
    }

    public void setApplySeq(Integer applySeq) {
        this.applySeq = applySeq;
    }

    public String getApplyRegId() {
        return applyRegId;
    }

    public void setApplyRegId(String applyRegId) {
        this.applyRegId = applyRegId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getJoinGubun() {
        return joinGubun;
    }

    public void setJoinGubun(String joinGubun) {
        this.joinGubun = joinGubun;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSetUpDate() {
        return setUpDate;
    }

    public void setSetUpDate(String setUpDate) {
        this.setUpDate = setUpDate;
    }

    public String getCompanyPhoneNum() {
        return companyPhoneNum;
    }

    public void setCompanyPhoneNum(String companyPhoneNum) {
        this.companyPhoneNum = companyPhoneNum;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getCompanyLicense() {
        return companyLicense;
    }

    public void setCompanyLicense(String companyLicense) {
        this.companyLicense = companyLicense;
    }



    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhoneNum() {
        return ownerPhoneNum;
    }

    public void setOwnerPhoneNum(String ownerPhoneNum) {
        this.ownerPhoneNum = ownerPhoneNum;
    }

    public String getOwnerBirth() {
        return ownerBirth;
    }

    public void setOwnerBirth(String ownerBirth) {
        this.ownerBirth = ownerBirth;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerAddr() {
        return ownerAddr;
    }

    public void setOwnerAddr(String ownerAddr) {
        this.ownerAddr = ownerAddr;
    }

    public List<String> getMemName() {
        return memName;
    }

    public void setMemName(List<String> memName) {
        this.memName = memName;
    }

    public List<String> getMemBirth() {
        return memBirth;
    }

    public void setMemBirth(List<String> memBirth) {
        this.memBirth = memBirth;
    }

    public List<String> getMemCompany() {
        return memCompany;
    }

    public void setMemCompany(List<String> memCompany) {
        this.memCompany = memCompany;
    }

    public List<String> getMemPosition() {
        return memPosition;
    }

    public void setMemPosition(List<String> memPosition) {
        this.memPosition = memPosition;
    }

    public List<String> getMemPhoneNum() {
        return memPhoneNum;
    }

    public void setMemPhoneNum(List<String> memPhoneNum) {
        this.memPhoneNum = memPhoneNum;
    }

    public List<String> getMemEmail() {
        return memEmail;
    }

    public void setMemEmail(List<String> memEmail) {
        this.memEmail = memEmail;
    }

    public String getApplyRoot() {
        return applyRoot;
    }

    public void setApplyRoot(String applyRoot) {
        this.applyRoot = applyRoot;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public List<XTechApplyMember> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<XTechApplyMember> memberList) {
        this.memberList = memberList;
    }

    @Override
    public String toString() {
        return "XTechApply{" +
                "applySeq=" + applySeq +
                ", applyRegId='" + applyRegId + '\'' +
                ", applyRegDate=" + applyRegDate +
                ", itemName='" + itemName + '\'' +
                ", joinGubun='" + joinGubun + '\'' +
                ", companyName='" + companyName + '\'' +
                ", setUpDate=" + setUpDate +
                ", companyPhoneNum='" + companyPhoneNum + '\'' +
                ", companyAddr='" + companyAddr + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhoneNum='" + ownerPhoneNum + '\'' +
                ", ownerBirth=" + ownerBirth +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", ownerAddr='" + ownerAddr + '\'' +
                ", memName=" + memName +
                ", memBirth=" + memBirth +
                ", memCompany=" + memCompany +
                ", memPosition=" + memPosition +
                ", memPhoneNum=" + memPhoneNum +
                ", memEmail=" + memEmail +
                ", memNameStr='" + memNameStr + '\'' +
                ", memBirthStr='" + memBirthStr + '\'' +
                ", memCompanyStr='" + memCompanyStr + '\'' +
                ", memPositionStr='" + memPositionStr + '\'' +
                ", memPhoneNumStr='" + memPhoneNumStr + '\'' +
                ", memEmailStr='" + memEmailStr + '\'' +
                ", applyRoot='" + applyRoot + '\'' +
                ", space='" + space + '\'' +
                '}';
    }
}
