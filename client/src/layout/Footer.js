function Footer() {
  return (
    <footer>
      <div className="bg-zinc-700 h-[200px]">
        <div className="flex flex-col space-y-[20px] w-3/4 h-full mx-auto justify-center">
          <div>
            <p className="text-white">사업자 등록 번호 : xxxxxxxx</p>
          </div>
          <div>
            <p className="text-white">CONTACT US : xxx-xxx-xxxx</p>
          </div>
          <div>
            <p className="text-white">개인정보 처리 방침</p>
          </div>
          <div>
            <p className="text-white">
              이 사이트에서 발생하는 모든 사건사고에 대해 책임 없음
            </p>
          </div>
        </div>
      </div>
    </footer>
  );
}
export default Footer;
