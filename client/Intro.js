function Intro() {
    return (
        <div className="flex items-center my-[70px] h-[200px]">
            <div className="w-1/3 h-full">
                <img src="image.png" alt="Introducing Image" className="h-full"></img>
            </div>
            <div className="w-2/3 h-100%">
                <p className="p-[10px]">
                    Lorem Ipsum is simply dummy text of the printing and typesetting
                    industry. Lorem Ipsum has been the industry's standard dummy text ever
                    since the 1500s, when an unknown printer took a galley of type and
                    scrambled it to make a type specimen book. It has survived not only
                    five centuries, but also the leap into electronic typesetting,
                    remaining essentially unchanged. It was popularised in the 1960s with
                    the release of Letraset sheets containing Lorem Ipsum passages, and
                    more recently with desktop publishing software like Aldus PageMaker
                    including versions of Lorem Ipsum.
                </p>
            </div>
        </div>
    );
}
export default Intro;