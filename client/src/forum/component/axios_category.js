//카테고리를 가져오는 함수
import axios from "axios";

//공모전 목록
export const contest_CategoryList = [
    "target", "organization", "category", "scale", "benefit", "startAt", "endAt"
];
export const contest_CategoryKOR = [
    "응모 대상", "주최 기관", "공모 분야", "수상 규모", "수상 혜택", "시작일", "마감일"
];
export const contest_CategoryTrans = {
    target:"응모 대상",
    organization:"주최 기관",
    category:"공모 분야",
    scale:"수상 규모",
    benefit:"수상 혜택",
    startAt:"시작일",
    endAt:"마감일",
}
//게시판용
export const contest_CategoryKeyList = {
    target: "targets",
    organization:"organizations",
    category:"categories",
    scale:"scales",
    benefit:"benefits",
};

//공모전 게시글
export const contest_CategoryKeyPost = {
    content:"content", title:"title",
    startAt:"startAt", endAt:"endAt",
    userId:"userId", benefit:"benefitId",
    category:"categoryId", organization:"organizationId",
    scale:"scaleId", target:"targetId"
}

export const contest_getCategoryPart = (key) => {
    contest_CategoryList.map(async() => {
        try {
            const response = await axios.get(`/postType/${key}`);
            const newKey = contest_CategoryKeyList[key];
            const jsonData = response.data.responseDto;
            return jsonData[newKey];
        } catch(error) {
            console.error("CategoryPart error!", key);
        }
    });
}

export const contest_getCategoryAll = async (list) => {
    const result = {};
    await Promise.all(
        list.map(async (key) => {
            try {
                const response = await axios.get(`/postType/${key}`);
                const jsonData = response.data.responseDto;
                const newKey = contest_CategoryKeyList[key];
                result[key] = jsonData[newKey];
            } catch (error) {
                console.error(key + "get 오류 발생");
            }
        })
    );
    return result;
};



//팀원모집
export const team_CategoryList = ["FRONT", "BACK", "AI", "ETC"];
export const team_CategoryKOR = ["Front-End", "Back-End", "AI", "기타"];
export const team_CategoryTrans = {
    FRONT:"Front-End",
    BACK:"Back-End",
    AI:"AI",
    ETC:"기타"
}
export const team_CategoryBackTrans = {
    "Front-End":"FRONT",
    "Back-End":"BACK",
    "AI":"AI",
    "기타":"ETC"
}

export const team_CategoryDetail = {
    FRONT:["React", "Vue.js", "Angular"],
    BACK:["Spring", "Django", "Ruby"],
    AI:["Tensorflow", "Keras", "PyTorch"],
    ETC:["디자인", "기획"]
};