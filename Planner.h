#pragma once
// can you see me?
//hihih
#include "lainlib.h"

#include "Item.h"
#include "DayList.h"

namespace planner {

	/**
	* @class: Planner
	* @author: Comcx
	* @date: 2018.2.13
	*
	*/
	class Planner {

	private:
		DayList daylist_;
		int time_code_ = 0;

		int time_code() const { return this->time_code_; }

	public:
		Planner() {}
		Planner(const std::string URL) {
			this->daylist_ = this->daylist_.get_daylist_from(URL);
		}
		Planner(const Planner &that): time_code_(that.time_code()), daylist_(that.list()) {}

		int start();
		bool refresh() {
			this->time_code_ = this->daylist_.pointer.syn();
			return this->time_code_ == this->daylist_.pointer.syn()? true : false;
		};
		Item get_new_item();
		Item item() const { return this->daylist_.item(); }
		Item item(int index) const { return this->daylist_.at(index); };
		DayList list() const { return this->daylist_; }
		std::string system_time() const;
		std::string panel();
		int search() const;


	};


	std::string Planner::system_time() const {
		time_t now = time(NULL);
		tm* t = localtime(&now);

		std::string ans("-> localTime: ");

		return ans + std::to_string(t->tm_hour) + ":" +
			std::to_string(t->tm_min) + ":" + std::to_string(t->tm_sec);
	}

	std::string Planner::panel() {
		this->refresh();

		std::string local_time = this->system_time();
		Item item = this->item();
		std::string main = item.main();
		std::string type = item.type();
		std::string note = item.note();

		// Process the actual time now
		int time_code = item.time();
		std::string time = std::to_string(time_code / 2 + 6);
		if (time_code % 2 == 1) {
			time += ":30 ~ " + std::to_string(time_code / 2 + 6 + 1) + ":00";
		} else {
			time += ":00 ~ " + std::to_string(time_code / 2 + 6) + ":30";
		}

		std::string menu = 
			"\n-> Refresh__1, Next__2, Skip__3, Show__4, Change note__5, Swap__6, Exit__0\n";

		return local_time + "\n" +
			"\n-> Main: " + main +
			"\n-> Time: " + time +
			"\n-> Type: " + type +
			"\n-> Note: " + note +
			"\n" + menu;

	}


	Item Planner::get_new_item() {
		int itemIndex = this->daylist_.pointer.number();
		std::cout << "-> Input your new item [format: main\ttype\tnote]" << std::endl;

		string line(""), main(""), type(""), note("");

		std::getline(std::cin, line);
		int counter = 0;
		for (char c : line) {
			if (c != '\t') {
				switch (counter) {
				case 0:
					main += c;
					break;
				case 1:
					type += c;
					break;
				case 2:
					note += c;
					break;
				default:
					std::cerr << "wrong format of input!";
				}//end switch
			}
			else {
				counter = counter + 1;
			}
		}//end for(char c : line)

		Item item(main, this->time_code(), type, note);
		this->daylist_[itemIndex] = item;

		return item;
	}


	int Planner::search() const {
		int ans_replaceable(-1), ans_free(-1), ans(-1);
		ans_replaceable = this->daylist_.search_by_type("REPLACABLE", 
			this->daylist_.pointer.number() + 1);

		ans_free = this->daylist_.search_by_type("FREE", this->daylist_.pointer.number() + 1);

		if (ans_replaceable == -1) ans = ans_free;
		else if (ans_free == -1) ans = ans_replaceable;
		else ans = ans_replaceable < ans_free? ans_replaceable : ans_free;
		return ans;
	}

	int Planner::start() {	//the main function of Planner

		bool dec_flag = true; while (dec_flag == true) {

			std::cout << this->panel() << std::endl;

			int order(-1); std::cin >> order;
			switch (order) {
			case 0:	//end planner
				dec_flag = false;
				break;

			case 1:	//to refresh the item and time now
				this->refresh();
				break;

			case 2:	//see what's next
				std::cout << this->item(this->daylist_.pointer.number() + 1);
				break;

			case 3:	//want to skip or adjust current item
				if (this->item().type() == "_DELAY" ||
					this->item().type() == "FREE" ||
					this->item().type() == "REPLACABLE") { // item can replaced directly

					std::cin.get();
					int currentIndex = this->daylist_.pointer.number();
					this->daylist_[currentIndex] = this->get_new_item();
				}
				else { // find other item to be replaced
					std::cin.get();

					int replacedIndex = this->search();
					if (replacedIndex == -1) { // find no item to be replaced
						std::cout << "-> found no item to be replaced" << std::endl;
						std::cout << "Still gonna to skip?__1, keep this__0" << std::endl;

						int confirmed(-1); std::cin >> confirmed;
						if (confirmed == -1) {
							std::cerr << "-> wrong order of confirming";
						} else if (confirmed) {
							std::cin.get();
							int currentIndex = this->daylist_.pointer.number();
							this->daylist_[currentIndex] = this->get_new_item();
						}

						break;
					}
					else { // replace found item
						int currentIndex = this->daylist_.pointer.number();
						this->daylist_[replacedIndex] = this->daylist_.at(currentIndex);
						this->daylist_[currentIndex] = this->get_new_item();
					}

				}
				break;

			case 4:	//see the whole list of the day
				std::cin.get();
				std::cout << this->daylist_;
				std::cout << "-> press [Enter] to close" << std::endl;

				std::cin.get();

				break;

			case 5:	//change something to note
				std::cin.get();
				{
					std::cout << "-> Input the new note: " << std::endl;

					std::string theNote("");
					std::getline(std::cin, theNote);
					int currentIndex = this->daylist_.pointer.number();
					this->daylist_[currentIndex].change_note(theNote);
				}

				break;

			case 6:
				std::cin.get();
				std::cout << this->daylist_;
				std::cout << "-> Input the index of the item you want to swap: " << std::endl;
				{
					int index(-1);
					std::cin >> index;
					int current_index = this->daylist_.pointer.number();
					Item tmp = this->daylist_[current_index];
					this->daylist_[current_index] = this->daylist_[index];
					this->daylist_[index] = tmp;
				}

				break;

			default:
				std::cerr << "wrong order!";
				//there's something wrong here, this function is not prefect.
				break;

			}// end switch

			Sleep(1000); // wait a sec
			system("cls"); // clear screen

		}//end while(decFlag)

		return 0;
	}




}// namespace planner