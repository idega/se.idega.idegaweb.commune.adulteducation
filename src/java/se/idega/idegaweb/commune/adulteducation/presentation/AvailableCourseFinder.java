package se.idega.idegaweb.commune.adulteducation.presentation;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourse;
import com.idega.block.school.data.School;
import com.idega.block.school.data.SchoolSeason;
import com.idega.block.school.data.SchoolStudyPath;
import com.idega.block.school.data.SchoolStudyPathGroup;
import com.idega.block.school.data.SchoolType;
import com.idega.presentation.IWContext;
import com.idega.presentation.PresentationObject;
import com.idega.presentation.Table;
import com.idega.presentation.text.Break;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.util.SelectorUtility;
import com.idega.util.IWTimestamp;


public class AvailableCourseFinder extends AdultEducationBlock {

	private static final String PARAMETER_ACTION = "acf_action";
	
	private static final String PARAMETER_SCHOOL_TYPE = "acf_school_type";
	private static final String PARAMETER_STUDY_PATH_GROUP = "acf_study_path_group";
	private static final String PARAMETER_SCHOOL = "acf_school";	

	private Object iSchoolTypePK;
	private SchoolType iSchoolType;	
	
	private Object iSchoolSeasonPK;
	private SchoolSeason iSchoolSeason;
	
	private Object iStudyPathGroupPK;
	private SchoolStudyPathGroup iStudyPathGroup;
	
	private Object iStudyPathPK;
	private SchoolStudyPath iStudyPath;
	
	private Object iSchoolPK;
	private School iSchool;
	
	private static final int ACTION_DEFAULT = 1;
	private static final int ACTION_SEARCH = 2;
	
	public static String NULL_COMMENT = "-";
		
	public void present(IWContext iwc) {
		int action = parseAction(iwc);
		add(getSearchForm());
		switch (action) {		
			case ACTION_SEARCH:
				add(getCourses(iwc));
				break;
		}		
	}	
	
	private PresentationObject getSearchForm() {
		try {
			Form form = new Form();
			SelectorUtility util = new SelectorUtility();
			
			Table table = new Table();
			table.setWidth(Table.HUNDRED_PERCENT);
			table.setCellpadding(3);
			table.setCellspacing(0);
			form.add(table);
			
			table.add(getSmallHeader(localize("type", "Type") + ":"), 1, 1);
			table.add(new Break(), 1, 1);
			DropdownMenu types = (DropdownMenu) getStyledInterface(util.getSelectorFromIDOEntities(new DropdownMenu(
					PARAMETER_SCHOOL_TYPE), getBusiness().getSchoolTypes(), "getLocalizationKey", getResourceBundle()));
			types.addMenuElementFirst("", localize("select_type", "Select type"));
			if (this.iSchoolTypePK != null) {
				types.setSelectedElement(this.iSchoolTypePK.toString());
			}
			types.setToSubmit();
			table.add(types, 1, 1);
			
			
			table.add(getSmallHeader(localize("season", "Season") + ":"), 2, 1); // aka Period
			table.add(new Break(), 2, 1);
			DropdownMenu seasons = (DropdownMenu) getStyledInterface(util.getSelectorFromIDOEntities(new DropdownMenu(
					PARAMETER_SCHOOL_SEASON), getBusiness().getCurrentSeasons(), "getSeasonName"));
			seasons.addMenuElementFirst("", localize("select_season", "Select season"));
			if (this.iSchoolSeasonPK != null) {
				seasons.setSelectedElement(this.iSchoolSeasonPK.toString());
			}
			seasons.setToSubmit();
			table.add(seasons, 2, 1);
			
			
			table.add(getSmallHeader(localize("study_path_group", "Study path group") + ":"), 3, 1); // aka Amnesomrade
			table.add(new Break(), 3, 1);
			DropdownMenu groups = (DropdownMenu) getStyledInterface(util.getSelectorFromIDOEntities(new DropdownMenu(
					PARAMETER_STUDY_PATH_GROUP), getBusiness().getStudyPathsGroups(), "getLocalizationKey",
					getResourceBundle()));
			groups.addMenuElementFirst("", localize("select_study_path_group", "Select group"));
			if (this.iStudyPathGroupPK != null) {
				groups.setSelectedElement(this.iStudyPathGroupPK.toString());
			}
			groups.setToSubmit();
			table.add(groups, 3, 1);
			
			
			table.add(getSmallHeader(localize("study_path", "Study path") + ":"), 1, 2); // aka Kurs
			table.add(new Break(), 1, 2);
			DropdownMenu studyPaths = (DropdownMenu) getStyledInterface(util.getSelectorFromIDOEntities(
					new DropdownMenu(PARAMETER_STUDY_PATH),
					getBusiness().getStydyPathsBySchoolTypeAndSchoolStudyPathGroup(this.iSchoolType, this.iStudyPathGroup),
					"getDescription"));
			studyPaths.addMenuElementFirst("", localize("select_study_path", "Select study path"));
			studyPaths.setToSubmit();
			if (this.iStudyPathPK != null) {
				studyPaths.setSelectedElement(this.iStudyPathPK.toString());
			}
			table.add(studyPaths, 1, 2);
			
			
			table.add(getSmallHeader(localize("school", "School") + ":"), 2, 2); // aka Anordnare
			table.add(new Break(), 2, 2);
			Collection schools = null;
			if (this.iSchoolSeasonPK != null && this.iStudyPathGroupPK != null && this.iSchoolTypePK != null) {
				schools = getBusiness().getAvailableSchools(this.iStudyPathPK, this.iSchoolSeasonPK, this.iStudyPathGroupPK, this.iSchoolTypePK);
			}
			DropdownMenu school = (DropdownMenu) getStyledInterface(new DropdownMenu(PARAMETER_SCHOOL));
			school.addMenuElementFirst("", localize("select_school", "Select school"));
			if (schools != null) {
				Iterator iterator = schools.iterator();
				while (iterator.hasNext()) {
					School element = (School) iterator.next();
					school.addMenuElement(element.getPrimaryKey().toString(), element.getSchoolName());
				}
			}
			if (this.iSchoolPK != null) {
				school.setSelectedElement(this.iSchoolPK.toString());
			}
			table.add(school, 2, 2);
			table.mergeCells(2, 2, 3, 2);			
			
			table.setAlignment(3, 3, "right");
			SubmitButton search = (SubmitButton) getButton(new SubmitButton(localize("search", "Search"),
					PARAMETER_ACTION, String.valueOf(ACTION_SEARCH)));			
			table.add(search, 3, 3);			
			search.setOnSubmitFunction("checkSearchParameters", getSubmitCheckScript());			
			form.setToDisableOnSubmit(search, true);
			
			return form;
		}
		catch (RemoteException e) {
			return null;
		}
	}	
	
	private String getSubmitCheckScript() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("\n");
		buffer.append("function checkSearchParameters() { \n\t");
		buffer.append("	var dropSchoolType = findObj('").append(PARAMETER_SCHOOL_TYPE).append("');	\n\t");
		buffer.append("	var dropSchoolSeason = findObj('").append(PARAMETER_SCHOOL_SEASON).append("');	\n\t");
		buffer.append("	var dropStudyPathGroup = findObj('").append(PARAMETER_STUDY_PATH_GROUP).append("');	\n\t");
		
		buffer.append("	var dropStudyPath = findObj('").append(PARAMETER_STUDY_PATH).append("');	\n\t");
		buffer.append("	var dropSchool = findObj('").append(PARAMETER_SCHOOL).append("');	\n\t");
		
		String message = localize("adult_education.not_all_required_fields_selected", "You have not selected all the fields required to make a search");
		buffer.append(" var message = '").append(message).append("';\n\t");	
		
		buffer.append(" if (dropSchoolType.selectedIndex == 0 || dropSchoolSeason.selectedIndex == 0 || dropStudyPathGroup.selectedIndex == 0) { \n\t");
		buffer.append("		alert(message);  \n\t");
		buffer.append("		return false;  \n\t");	
		buffer.append("	}\n\t");	
		buffer.append("	\n\t");	
		
		buffer.append(" if (dropStudyPath.selectedIndex == 0 && dropSchool.selectedIndex == 0) { \n\t");
		buffer.append("		alert(message);  \n\t");
		buffer.append("		return false;  \n\t");	
		buffer.append("	}\n\t");	
		buffer.append("	\n\t");	

		buffer.append("	return true;	\n\t");
		buffer.append("}\n\t");	
				
		return buffer.toString();		
	}

	
	
	private int parseAction(IWContext iwc) {
					
		if (iwc.isParameterSet(PARAMETER_SCHOOL_TYPE)) {
			this.iSchoolTypePK = iwc.getParameter(PARAMETER_SCHOOL_TYPE);
		}	
		
		if (iwc.isParameterSet(PARAMETER_SCHOOL_SEASON)) {
			this.iSchoolSeasonPK = iwc.getParameter(PARAMETER_SCHOOL_SEASON);
		}		
		
		if (iwc.isParameterSet(PARAMETER_STUDY_PATH_GROUP)) {
			this.iStudyPathGroupPK = iwc.getParameter(PARAMETER_STUDY_PATH_GROUP);
		}
		
		if (iwc.isParameterSet(PARAMETER_STUDY_PATH)) {
			this.iStudyPathPK = iwc.getParameter(PARAMETER_STUDY_PATH);
		}	
		
		if (iwc.isParameterSet(PARAMETER_SCHOOL)) {
			this.iSchoolPK = iwc.getParameter(PARAMETER_SCHOOL);
		}			
		
		try {
			if (this.iSchoolTypePK != null) {
				this.iSchoolType = getBusiness().getSchoolBusiness().getSchoolType(this.iSchoolTypePK);
			}
			if (this.iSchoolSeasonPK != null) {
				this.iSchoolSeason = getBusiness().getSchoolBusiness().getSchoolSeason(this.iSchoolSeasonPK);
			}
			if (this.iStudyPathGroupPK != null) {
				this.iStudyPathGroup = getBusiness().getStudyPathBusiness().findStudyPathGroup(this.iStudyPathGroupPK);
			}
			if (this.iStudyPathPK != null ) {
				this.iStudyPath = getBusiness().getStudyPathBusiness().findStudyPath(this.iStudyPathPK);
			}
			if (this.iSchoolPK != null) {
				this.iSchool = getBusiness().getSchoolBusiness().getSchool(this.iSchoolPK);
			}
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}		
		
		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			return Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}
		return ACTION_DEFAULT;
	}
	
	
	private Table getCourses(IWContext iwc) {
		Table table = new Table();
		table.setWidth(Table.HUNDRED_PERCENT);
		table.setCellpadding(getCellpadding());
		table.setCellspacing(getCellspacing());
		table.setColumns(6);
		table.setRowColor(1, getHeaderColor());		
		int row = 1;
		int column = 1;		
		
		table.add(getLocalizedSmallHeader("provider", "Provider"), column++, row);
		table.add(getLocalizedSmallHeader("study_path", "Study path"), column++, row);
		table.add(getLocalizedSmallHeader("course_code", "Course code"), column++, row);
		table.add(getLocalizedSmallHeader("course_start", "Course start"), column++, row);
		table.add(getLocalizedSmallHeader("course_end", "Course end"), column++, row);
		table.add(getLocalizedSmallHeader("length", "Length"), column++, row++);
		
		//chek if parameters are supplied
		if (this.iSchoolType == null || this.iSchoolSeason == null || this.iStudyPathGroup == null) {
			return null;
		}
		if (this.iStudyPathGroup == null && this.iSchool == null) {
			return null;
		}		
		
		try {
			Collection courses = getBusiness().findAvailableCourses(this.iSchoolType, this.iSchoolSeason, 
					this.iStudyPathGroup, this.iStudyPath, this.iSchool);
			
			Iterator iter = courses.iterator();
			while (iter.hasNext()) {
				AdultEducationCourse course = (AdultEducationCourse) iter.next();
				SchoolStudyPath path = course.getStudyPath();
				School school = course.getSchool();
				
				column = 1;
				row++;
				table.setRowColor(row, getZebraColor1());
				
				table.add(getSmallText(school.getName()), column++, row);
				table.add(getSmallText(path.getDescription()), column++, row);
				table.add(getSmallText(course.getCode()), column++, row);
				
				IWTimestamp start = new IWTimestamp(course.getStartDate());
				table.add(getSmallText(start.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT)), column++, row);
								
				IWTimestamp end = new IWTimestamp(course.getEndDate());
				table.add(getSmallText(end.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT)), column++, row);				
				
				table.setAlignment(column, row, "center");
				table.add(getSmallText(Integer.toString(course.getLength())), column++, row);
				
				
				table.add("", 1, ++row);				
				table.setRowHeight(row, "1");
				table.mergeCells(1, row, 6, row);

				table.setRowStyle(row, "border-top", "1px solid black");
				table.setRowStyle(row, "font-size", "0");
				table.setRowStyle(row, "padding", "0");
				
				column = 2;
				String comment = course.getComment();
				comment = (comment == null) ? NULL_COMMENT : comment;  
				table.add(getSmallText(comment), column, ++row);				
				table.mergeCells(column, row, 6, row);			
				table.setStyle(1, row, "background-color", getZebraColor1());
				table.add(Text.NON_BREAKING_SPACE, 1, row);
				table.setStyle(2, row, "background-color", getZebraColor2());
				
				table.add("", 1, ++row); //spacer
				table.setStyle(1, row, "padding", ".5em");
				
			}
			
		}
		catch (RemoteException e) {			
			e.printStackTrace();
		}

		return table;
	}
	
}
