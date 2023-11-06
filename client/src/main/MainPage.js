import Footer from "../layout/Footer";
import Nav from "../layout/Nav";
import Ad from "./Ad";
import Statistics from "./Statistics";
import Intro from "./Intro";

function MainPage() {
  return (
    <div>
      <Nav></Nav>
      <main className="flex justify-center">
        <div className="w-3/4 flex justify-center">
          <div className="w-full my-[50px]">
            <Ad></Ad>
            <Statistics></Statistics>
            <Intro></Intro>
          </div>
        </div>
      </main>
      <Footer></Footer>
    </div>
  );
}


export default MainPage;
