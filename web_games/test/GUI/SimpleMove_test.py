# -*- coding: utf-8 -*-
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import NoAlertPresentException
import unittest, time, re, xmlrunner

class SimpleMove(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.PhantomJS()
        self.driver.implicitly_wait(30)
        self.base_url = "http://test"
        self.verificationErrors = []
        self.accept_next_alert = True
    
    def test_simple_move(self):
        driver = self.driver
        driver.get(self.base_url + "/games/chessSingleClient.htm")
        self.assertEqual("Play Chess (same computer) - Brasee.com", driver.title)
        try: self.assertEqual("", driver.find_element_by_id("a3").text)
        except AssertionError as e: self.verificationErrors.append(str(e))
        self.assertEqual("", driver.find_element_by_id("a3").text)
        # ERROR: Caught exception [ERROR: Unsupported command [getTable | id=chessboard.5.0 | ]]
        self.assertTrue(self.is_element_present(By.ID, "a3"))
    
    def is_element_present(self, how, what):
        try: self.driver.find_element(by=how, value=what)
        except NoSuchElementException as e: return False
        return True
    
    def is_alert_present(self):
        try: self.driver.switch_to_alert()
        except NoAlertPresentException as e: return False
        return True
    
    def close_alert_and_get_its_text(self):
        try:
            alert = self.driver.switch_to_alert()
            alert_text = alert.text
            if self.accept_next_alert:
                alert.accept()
            else:
                alert.dismiss()
            return alert_text
        finally: self.accept_next_alert = True
    
    def tearDown(self):
        self.driver.quit()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main(testRunner=xmlrunner.XMLTestRunner(output='test_reports'))

