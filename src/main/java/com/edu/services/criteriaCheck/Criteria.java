package com.edu.services.criteriaCheck;

import com.edu.mvc.models.Criterion;

public class Criteria {

    //CONTENT CRITERIA

    public static Criterion C1 = createCriteria(
            1,
            "C1",
            "размещены сведения о дате создания ОУ",
            "сведения о дате создания образовательного учреждения",
            "");
    public static Criterion C2 = createCriteria(
            2,
            "C2",
            "размещены сведения о структуре ОУ",
            "сведения о структуре образовательного учреждения",
            "");
    public static Criterion C3 = createCriteria(
            3,
            "C3",
            "размещены сведения об образовательных программах",
            "сведения о реализуемых основных и дополнительных образовательных программах с указанием численности лиц, обучающихся за счет средств соответствующего бюджета бюджетной системы Российской Федерации, по договорам с физическими и (или) юридическими лицами с оплатой ими стоимости обучения",
            "");
    public static Criterion C4 = createCriteria(
            4,
            "C4",
            "размещены сведения об образовательных стандартах и о требованиях",
            "сведения об образовательных стандартах и о требованиях, самостоятельно установленных федеральными государственными образовательными учреждениями высшего профессионального образования в соответствии с пунктом 2 статьи 7 настоящего Закона (при их наличии)",
            "");
    public static Criterion C5 = createCriteria(
            5,
            "C5",
            "размещены сведения о педагогическом составе",
            "сведения о персональном составе педагогических работников с указанием уровня образования и квалификации",
            "");
    public static Criterion C6 = createCriteria(
            6,
            "C6",
            "размещены сведения о материально-техническом обеспечении и об оснащенности образовательного процесса",
            "сведения о материально-техническом обеспечении и об оснащенности образовательного процесса (в том числе о наличии библиотеки, общежитий, спортивных сооружений, об условиях питания, медицинского обслуживания, о доступе к информационным системам и информационно-телекоммуникационным сетям)",
            "");
    public static Criterion C7 = createCriteria(
            7,
            "C7",
            "размещены сведения об электронных ресурсах",
            "сведения об электронных образовательных ресурсах, доступ к которым обеспечивается обучающимся",
            "");
    public static Criterion C8 = createCriteria(
            8,
            "C8",
            "размещены сведения о научно-исследовательской деятельности",
            "сведения о направлениях научно-исследовательской деятельности и базе для ее осуществления (для образовательных учреждений высшего профессионального образования)",
            "");
    public static Criterion C9 = createCriteria(
            9,
            "C9",
            "размещены сведения о результатах приема",
            "сведения о результатах приема по каждому направлению подготовки (специальности) среднего профессионального образования или высшего профессионального образования, по различным условиям приема (прием на обучение, финансируемое за счет средств соответствующего бюджета бюджетной системы Российской Федерации, по договорам с физическими и (или) юридическими лицами с оплатой ими стоимости обучения) с указанием средней суммы набранных баллов по всем вступительным испытаниям (только для образовательных учреждений, реализующих основные профессиональные образовательные программы среднего профессионального образования и (или) высшего профессионального образования)",
            "");
    public static Criterion C10 = createCriteria(
            10,
            "C10",
            "размещены сведения о наличии материальной поддержки",
            "сведения о наличии стипендий и иных видов материальной поддержки, об условиях предоставления их обучающимся",
            "");
    public static Criterion C11 = createCriteria(
            11,
            "C11",
            "размещены сведения о финансах и материальных средствах",
            "сведения о поступлении и расходовании финансовых и материальных средств по итогам финансового года",
            "");
    public static Criterion C12 = createCriteria(
            12,
            "C12",
            "размещена копия лицензии",
            "копия документа, подтверждающего наличие лицензии на осуществление образовательной деятельности (с приложениями)",
            "");
    public static Criterion C13 = createCriteria(
            13,
            "C13",
            "размещена копия свидетельства о государственной аккредитации",
            "копия свидетельства о государственной аккредитации (с приложениями)",
            "");
    public static Criterion C14 = createCriteria(
            14,
            "C14",
            "размещена копия плана финансово-хозяйственной деятельности или бюджетной сметы образовательного учреждения",
            "копия утвержденных в установленном порядке плана финансово-хозяйственной деятельности или бюджетной сметы образовательного учреждения",
            "");
    public static Criterion C15 = createCriteria(
            15,
            "C15",
            "размещен отчет о результатах самообследования",
            "отчет о результатах самообследования",
            "");
    public static Criterion C16 = createCriteria(
            16,
            "C16",
            "размещены сведения об оказании платных услуг",
            "порядок оказания платных образовательных услуг, образец договора об оказании платных образовательных услуг, с указанием стоимости платных образовательных услуг",
            "");

    //STRUCTURE CRITERIA

    public static Criterion S1 = createCriteria(
            17,
            "S1",
            "структура содержит специальный раздел «Сведения об образовательной организации»",
            "Для размещения информации на Сайте должен быть создан специальный раздел «Сведения об образовательной организации»",
            "");
    public static Criterion S2 = createCriteria(
            18,
            "S2",
            "специальный раздел содержит \"Основные сведения\"",
            "Специальный раздел должен содержать подраздел \"Основные сведения\"",
            "");
    public static Criterion S3 = createCriteria(
            19,
            "S3",
            "специальный раздел содержит \"Структура и органы управления образовательной организацией\"",
            "Специальный раздел должен содержать подраздел \"Структура и органы управления образовательной организацией\"",
            "");
    public static Criterion S4 = createCriteria(
            20,
            "S4",
            "специальный раздел содержит \"Документы\"",
            "Специальный раздел должен содержать подраздел \"Документы\"",
            "");
    public static Criterion S5 = createCriteria(
            21,
            "S5",
            "специальный раздел содержит \"Образование\"",
            "Специальный раздел должен содержать подраздел \"Образование\"",
            "");
    public static Criterion S6 = createCriteria(
            22,
            "S6",
            "специальный раздел содержит \"Образовательные стандарты\"",
            "Специальный раздел должен содержать подраздел \"Образовательные стандарты\"",
            "");
    public static Criterion S7 = createCriteria(
            23,
            "S7",
            "специальный раздел содержит \"Руководство. Педагогический (научно-педагогический) состав\"",
            "Специальный раздел должен содержать подраздел \"Руководство. Педагогический (научно-педагогический) состав\"",
            "");
    public static Criterion S8 = createCriteria(
            24,
            "S8",
            "специальный раздел содержит \"Материально-техническое обеспечение и оснащенность образовательного процесса\"",
            "Специальный раздел должен содержать подраздел \"Материально-техническое обеспечение и оснащенность образовательного процесса\"",
            "");
    public static Criterion S9 = createCriteria(
            25,
            "S9",
            "специальный раздел содержит \"Стипендии и иные виды материальной поддержки\"",
            "Специальный раздел должен содержать подраздел \"Стипендии и иные виды материальной поддержки\"",
            "");
    public static Criterion S10 = createCriteria(
            26,
            "S10",
            "специальный раздел содержит \"Платные образовательные услуги\"",
            "Специальный раздел должен содержать подраздел \"Платные образовательные услуги\"",
            "");
    public static Criterion S11 = createCriteria(
            27,
            "S11",
            "специальный раздел содержит \"Финансово-хозяйственная деятельность\"",
            "Специальный раздел должен содержать подраздел \"Финансово-хозяйственная деятельность\"",
            "");
    public static Criterion S12 = createCriteria(
            28,
            "S12",
            "специальный раздел содержит \"Вакантные места для приема (перевода)\"",
            "Специальный раздел должен содержать подраздел \"Вакантные места для приема (перевода)\"",
            "");

    //DOCUMENT CRITERIA

    public static Criterion D1 = createCriteria(
            29,
            "D1",
            "документ представлен в верном формате",
            "Файлы документов представляются на Сайте в форматах Portable Document Files (.pdf), Microsoft Word / Microsofr Excel (.doc, .docx, .xls, .xlsx), Open Document Files (.odt, .ods).",
            "");
    public static Criterion D2 = createCriteria(
            30,
            "D2",
            "документ не превышает максимальный размер",
            "максимальный размер размещаемого файла не должен превышать 15 мб. Если размер файла превышает максимальное значение, то он должен быть разделен на несколько частей (файлов), размер которых не должен превышать максимальное значение размера файла",
            "");
    public static Criterion D3 = createCriteria(
            31,
            "D3",
            "документ имеет разрешение не менее 75 dpi",
            "сканирование документа должно быть выполнено с разрешением не менее 75 dpi",
            "");
    public static Criterion D4 = createCriteria(
            32,
            "D4",
            "документ читаемый",
            "отсканированный текст в электронной копии документа должен быть читаемым",
            "");

    public static Criterion[] CONTENT_CRITERIA = {C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15, C16};
    public static Criterion[] STRUCTURE_CRITERIA = {S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12};
    public static Criterion[] DOCUMENT_CRITERIA = {D1, D2, D3, D4};

    private static Criterion createCriteria(int criteriaId, String shortDescription, String name, String longDescription, String dictionary) {
        Criterion criterion = new Criterion();
        criterion.setCriteriaId(criteriaId);
        criterion.setShortDescription(shortDescription);
        criterion.setName(name);
        criterion.setLongDescription(longDescription);
        criterion.setDictionary(dictionary);
        return criterion;
    }
}
