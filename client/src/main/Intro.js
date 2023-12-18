function Intro() {
    return (
        <div className="flex items-center my-[70px] h-[200px] ml-40">
            <div className="w-1/3 h-full mr-8">
                {/* 여기서 마진을 추가합니다 */}
                <img src="image.png" alt="Introducing Image" className="h-full" />
            </div>
            <div className="w-2/3 h-full ml-[10px]">
                <h3 className="mb-4 text-xl font-semibold">PM은 <strong>P</strong>roject <strong>M</strong>anager의 약자로,</h3>
                <p className="mb-2 text-lg">사용자들이 원하던, 사용자들이 필요한 기능을 담은</p>
                <p className="mb-2 text-lg">최고의 팀빌딩 서비스입니다.</p>
                <p className="mb-2 text-lg">원하는 공모전이나 프로젝트를 검색하고,</p>
                <p className="text-lg">원하는 팀에 지원하여 최고의 팀을 만들어보세요.</p>
            </div>
        </div>
    );
}

export default Intro;
